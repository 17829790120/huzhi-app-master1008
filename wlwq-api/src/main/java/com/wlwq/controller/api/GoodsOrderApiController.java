package com.wlwq.controller.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.aliyuncs.exceptions.ServerException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.GoodsOrder;
import com.wlwq.api.domain.ResourceFilePost;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.IGoodsOrderService;
import com.wlwq.api.service.IResourceFilePostService;
import com.wlwq.bestPay.constant.ModuleConstant;
import com.wlwq.bestPay.enums.BestPayTypeEnum;
import com.wlwq.bestPay.mq.RabbitMQSendService;
import com.wlwq.bestPay.params.PayRequest;
import com.wlwq.bestPay.payService.BestPayService;
import com.wlwq.common.annotation.RepeatSubmit;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.params.order.GoodsConfirmOrderParams;
import com.wlwq.params.order.GoodsOrderParams;
import com.wlwq.service.TokenService;
import com.wlwq.system.service.ISysConfigService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 商品订单信息Controller
 *
 * @author gaoce
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/order")
public class GoodsOrderApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IGoodsOrderService orderService;

    private final BestPayService payService;

    private final RabbitMQSendService rabbitMQSendService;

    private final ISysConfigService configService;

    private final IResourceFilePostService resourceFilePostService;

    /**
     * 单个商品确认订单
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/getGoodsBookConfirmOrder")
    public ApiResult getGoodsBookConfirmOrder(@Validated GoodsConfirmOrderParams params, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }

        //商品信息
        List<Object> goodsList = new ArrayList<>();
        //商品类型（0：书城商品 1：商品 2：课程商品）
        ResourceFilePost goods = getGoodsBooks(params.getGoodsId());
        goodsList.add(goods);

        //判断该商品是否需要运费,是否有运费（1：是， 2：否）
        BigDecimal freightPrice = BigDecimal.ZERO;

        // 商品总价价格
        //BigDecimal goodsTotalPrice = goods.getPrice().multiply(BigDecimal.valueOf(params.getNum())).setScale(2, RoundingMode.HALF_UP);
        BigDecimal goodsTotalPrice = goods.getPrice().setScale(2, RoundingMode.HALF_UP);

        // 支付金额 = 商品价格 + 运费
        BigDecimal payPrice = goodsTotalPrice.add(freightPrice);

        if (payPrice.compareTo(BigDecimal.ZERO) < 0) {
            payPrice = new BigDecimal("0.00");
        }

        //返回结果对象
        Map<String, Object> data = new HashMap<>(8);
        //商品信息
        data.put("goodsList", goodsList);
        //购买数量
        data.put("num", 1);
        //运费
        data.put("freightPrice", freightPrice);
        //商品总价
        data.put("goodsTotalPrice", goodsTotalPrice);
        //支付金额
        data.put("payPrice", payPrice);
        return ApiResult.ok(data);
    }


    /**
     * 单个商品下单(苹果)
     *
     * @param params
     * @throws ServerException
     */
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    @PostMapping(value = "/goodsBookToApplePay")
    public ApiResult goodsBookToApplePay(@Validated GoodsOrderParams params, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }

        //商品信息
        List<Object> goodsList = new ArrayList<>();
        //商品类型（0：书城商品 1：商品 2：课程商品）
        ResourceFilePost goods = getGoodsBooks(params.getGoodsId());
        goodsList.add(goods);

        //优惠金额
        BigDecimal couponPrice = BigDecimal.ZERO;

        String orderId = IdUtil.getSnowflake(1, 1).nextIdStr();
        String orderSn = IdUtil.getSnowflake(1, 1).nextIdStr();

        //主订单信息
        GoodsOrder order = GoodsOrder.builder().build();
        BigDecimal goodsTotalPrice = goods.getApplePrice().multiply(new BigDecimal(params.getPayNum())).setScale(2, RoundingMode.HALF_UP);

        //判断商品是否有运费 是否有运费（1：是， 2：否）
        BigDecimal freightPrice = BigDecimal.ZERO;

        BigDecimal price = goodsTotalPrice.add(freightPrice).subtract(couponPrice);

        order.setOrderId(orderId);
        order.setOrderSn(orderSn);
        //商品类型（0：书城商品 1：商品 2：课程商品）
        order.setOrderType(0);
        order.setAccountId(account.getAccountId());

        order.setTotalPrice(price.setScale(2, RoundingMode.HALF_UP));

        BigDecimal payPrice = price.setScale(2, RoundingMode.HALF_UP);
        if (payPrice.compareTo(BigDecimal.ZERO) < 0) {
            payPrice = new BigDecimal("0.00");
        }
        order.setPayPrice(payPrice);
        //订单状态（0：待付款 1：待发货 2：待收货 3：已完成  4：退款/售后  5：已取消 6：已关闭）
        order.setOrderStatus("0");
        order.setPayType(params.getPayType());
        order.setPayStatus(0);
        order.setAppId(params.getAppId());
        order.setModuleName(ModuleConstant.GOOD_ORDER_MODULE);

        order.setNickName(account.getNickName());
        order.setHeadPortrait(account.getHeadPortrait());
        order.setGoodsId(goods.getResourceFilePostId());
        order.setGoodsName(goods.getInformationPostTitle());
        order.setGoodsPrice(goods.getApplePrice());
        order.setCompanyId(goods.getCompanyId());
        order.setInformationPostFile(goods.getInformationPostFile());
        order.setInformationPostImages(goods.getInformationPostImages());
        order.setFileSize(goods.getFileSize());
        int flag;
        if (order.getPayPrice().compareTo(BigDecimal.ZERO) <= 0) {
            order.setPayTime(new Date());
            order.setOrderStatus("3");
            order.setPayType("free");
            order.setPayStatus(1);
            flag = orderService.insertGoodsOrder(order);
            if (flag < 1) {
                throw new ApiException("下单失败！");
            }
            return ApiResult.result(ApiCode.PAY_SUCCESS);
        }
        flag = orderService.insertGoodsOrder(order);
        if (flag < 1) {
            throw new ApiException("下单失败！");
        }
        // 将订单信息发送到延时队列，30分钟后还未支付成功则关闭
        String goodsOrderTimeout = Optional.ofNullable(configService.selectConfigByKey("goods_order_timeout")).orElse("30");
        Long expiration = new BigDecimal(goodsOrderTimeout).multiply(BigDecimal.valueOf(60L)).multiply(BigDecimal.valueOf(1000L)).longValue();
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        jsonObject.put("moduleName", ModuleConstant.GOOD_ORDER_PAY_TIMEOUT);
        //jsonObject.put("paramsStr", order.getOrderId());
        jsonObject.put("messageContent", order.getOrderId());
        rabbitMQSendService.sendDelayMessage(expiration, jsonObject.toString());

        // 苹果支付
        return ApiResult.ok(orderId);
    }

    /**
     * 单个商品下单
     *
     * @param params
     * @throws ServerException
     */
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    @PostMapping(value = "/goodsBookToPay")
    public ApiResult goodsBookToPay(@Validated GoodsOrderParams params, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }

        //商品信息
        List<Object> goodsList = new ArrayList<>();
        //商品类型（0：书城商品 1：商品 2：课程商品）
        ResourceFilePost goods = getGoodsBooks(params.getGoodsId());
        goodsList.add(goods);

        //优惠金额
        BigDecimal couponPrice = BigDecimal.ZERO;

        String orderId = IdUtil.getSnowflake(1, 1).nextIdStr();
        String orderSn = IdUtil.getSnowflake(1, 1).nextIdStr();

        //主订单信息
        GoodsOrder order = GoodsOrder.builder().build();
        BigDecimal goodsTotalPrice = goods.getPrice().multiply(new BigDecimal(params.getPayNum())).setScale(2, RoundingMode.HALF_UP);

        //判断商品是否有运费 是否有运费（1：是， 2：否）
        BigDecimal freightPrice = BigDecimal.ZERO;

        BigDecimal price = goodsTotalPrice.add(freightPrice).subtract(couponPrice);

        order.setOrderId(orderId);
        order.setOrderSn(orderSn);
        //商品类型（0：书城商品 1：商品 2：课程商品）
        order.setOrderType(0);
        order.setAccountId(account.getAccountId());

        order.setTotalPrice(price.setScale(2, RoundingMode.HALF_UP));

        BigDecimal payPrice = price.setScale(2, RoundingMode.HALF_UP);
        if (payPrice.compareTo(BigDecimal.ZERO) < 0) {
            payPrice = new BigDecimal("0.00");
        }
        order.setPayPrice(payPrice);
        //订单状态（0：待付款 1：待发货 2：待收货 3：已完成  4：退款/售后  5：已取消 6：已关闭）
        order.setOrderStatus("0");
        order.setPayType(params.getPayType());
        order.setPayStatus(0);
        order.setAppId(params.getAppId());
        order.setModuleName(ModuleConstant.GOOD_ORDER_MODULE);

        order.setNickName(account.getNickName());
        order.setHeadPortrait(account.getHeadPortrait());
        order.setGoodsId(goods.getResourceFilePostId());
        order.setGoodsName(goods.getInformationPostTitle());
        order.setGoodsPrice(goods.getPrice());
        order.setCompanyId(goods.getCompanyId());
        order.setInformationPostFile(goods.getInformationPostFile());
        order.setInformationPostImages(goods.getInformationPostImages());
        order.setFileSize(goods.getFileSize());
        int flag;
        if (order.getPayPrice().compareTo(BigDecimal.ZERO) <= 0) {
            order.setPayTime(new Date());
            order.setOrderStatus("3");
            order.setPayType("free");
            order.setPayStatus(1);
            flag = orderService.insertGoodsOrder(order);
            if (flag < 1) {
                throw new ApiException("下单失败！");
            }
            return ApiResult.result(ApiCode.PAY_SUCCESS);
        }
        flag = orderService.insertGoodsOrder(order);
        if (flag < 1) {
            throw new ApiException("下单失败！");
        }
        // 将订单信息发送到延时队列，30分钟后还未支付成功则关闭
        String goodsOrderTimeout = Optional.ofNullable(configService.selectConfigByKey("goods_order_timeout")).orElse("30");
        Long expiration = new BigDecimal(goodsOrderTimeout).multiply(BigDecimal.valueOf(60L)).multiply(BigDecimal.valueOf(1000L)).longValue();
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        jsonObject.put("moduleName", ModuleConstant.GOOD_ORDER_PAY_TIMEOUT);
        //jsonObject.put("paramsStr", order.getOrderId());
        jsonObject.put("messageContent", order.getOrderId());
        rabbitMQSendService.sendDelayMessage(expiration, jsonObject.toString());

        // 微信支付宝支付
        return ApiResult.ok(payService.placeOrder(PayRequest.builder()
                .appId(params.getAppId())
                .openid(account.getWxAppletOpenid())
                .payTypeEnum(BestPayTypeEnum.getByCode(params.getPayType()))
                .timeOutValue(Integer.valueOf(goodsOrderTimeout))
                .orderId(orderSn)
                .orderAmount(order.getPayPrice())
                .orderName("商品购买")
                .moduleName(ModuleConstant.GOOD_ORDER_MODULE)
                .build()));
    }

    /**
     * 删除订单
     *
     * @param orderId
     * @param request
     * @return
     */
    @GetMapping(value = "/deleteOrder")
    public ApiResult deleteOrder(HttpServletRequest request, @RequestParam(defaultValue = "0") String orderId) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }
        int flag = orderService.deleteGoodsOrderById(orderId);
        if (flag < 1) {
            throw new ApiException("删除失败！");
        }
        return ApiResult.ok("删除成功！");
    }

    /**
     * 用户订单列表
     *
     * @param keyword   关键字搜索
     * @param pageParam
     * @param request
     * @return
     */
    @GetMapping(value = "/findOrderList")
    public ApiResult findOrderList(PageParam pageParam, String keyword, HttpServletRequest request) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<GoodsOrder> result = orderService.selectGoodsOrderList(GoodsOrder
                .builder()
                .accountId(account.getAccountId())
                .orderStatus("3")
                .delStatus(0)
                .goodsName(keyword)
                .build());
        PageInfo<GoodsOrder> page = new PageInfo<>(result);
        return ApiResult.ok(page);
    }

    /**
     * 待付款，重新支付
     *
     * @param orderId 订单ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    @PostMapping(value = "/repayments")
    public ApiResult repayments(String appId, String payType, @RequestParam(defaultValue = "0") String orderId) {
        if (StringUtils.isEmpty(appId)) {
            return ApiResult.fail("appId不能为空！");
        }
        if (StringUtils.isEmpty(payType)) {
            return ApiResult.fail("支付方式不能为空！");
        }
        GoodsOrder order = orderService.selectGoodsOrderById(orderId);
        if (order == null) {
            return ApiResult.fail("没有该订单信息，请核对！");
        }
        ApiAccount account = accountService.selectApiAccountById(order.getAccountId());
        if (account == null) {
            return ApiResult.fail("该用户不存在，请核对！");
        }
        //是否支付（0：否 1：是）
        if (order.getPayStatus() == 1) {
            return ApiResult.fail("订单已支付或已关闭！");
        }
        String orderSn = IdUtil.getSnowflake(1, 1).nextIdStr();
        // 修改订单支付方式
        int flag = orderService.updateGoodsOrder(GoodsOrder.builder()
                .orderId(order.getOrderId())
                .orderSn(orderSn)
                .payType(payType)
                .appId(appId)
                .build());
        if (flag < 1) {
            throw new ApiException("修改订单异常！");
        }
        // 微信支付宝支付
        return ApiResult.ok(payService.placeOrder(PayRequest.builder()
                .appId(appId)
                .openid(account.getWxAppletOpenid())
                .payTypeEnum(BestPayTypeEnum.getByCode(payType))
                .timeOutValue(30)
                .orderId(orderSn)
                .orderAmount(order.getPayPrice())
                .orderName("商品购买")
                .moduleName(ModuleConstant.GOOD_ORDER_MODULE)
                .build()));
    }


    /**
     * 订单详情
     *
     * @param orderId 订单Id
     * @param request
     * @return
     */
    @GetMapping("/orderDetails")
    public ApiResult orderDetails(@RequestParam(defaultValue = "0") String orderId, HttpServletRequest request) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }
        GoodsOrder order = orderService.selectGoodsOrderById(orderId);
        if (order == null) {
            return ApiResult.fail("没有该订单信息，请核对！");
        }
        Map<String, Object> orderMap = BeanUtil.beanToMap(order);
        orderMap.put("ResourceFilePost", resourceFilePostService.selectResourceFilePostById(order.getGoodsId()));
        orderMap.put("order", order);
        orderMap.put("account", account);
        return ApiResult.ok(orderMap);
    }

    /**
     * 删除订单
     *
     * @param orderId 订单ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/delOrder")
    public ApiResult delOrder(@RequestParam(defaultValue = "0") String orderId, HttpServletRequest request) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }
        //获取订单信息
        GoodsOrder order = orderService.selectGoodsOrderById(orderId);
        if (order == null) {
            return ApiResult.fail("订单不存在，请核对！");
        }
        if (!order.getAccountId().equals(account.getAccountId())) {
            return ApiResult.fail("这不是您的订单哦！");
        }
        // * 订单状态（0：待付款 1：待发货 2：待收货 3：已完成  4：退款/售后  5：已取消 6：已关闭）
        if ("1".equals(order.getOrderStatus()) || "2".equals(order.getOrderStatus())) {
            return ApiResult.fail("进行中的订单不能删除！");
        }
        int flag = orderService.deleteGoodsOrderById(order.getOrderId());
        if (flag < 1) {
            throw new ApiException("删除失败！");
        }
        return ApiResult.ok("删除成功！");
    }

    /**
     * 查看商品状态是否正常
     *
     * @param goodsId
     * @return
     */
    private ResourceFilePost getGoodsBooks(String goodsId) {
        ResourceFilePost goodsBook = resourceFilePostService.selectResourceFilePostById(goodsId);
        if (StringUtils.isNull(goodsBook)) {
            throw new ApiException("未获取到商品信息！");
        }
        if ("1".equals(goodsBook.getDelStatus())) {
            throw new ApiException("该商品已失效！");
        }
        return goodsBook;
    }

    /**
     * 用户订单统计
     *
     * @param request
     * @return
     */
/*    @GetMapping(value = "/getOrderTotal")
    public ApiResult getOrderTotal(HttpServletRequest request) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail(ApiCode.DONT_LOGIN);
        }
        Map<String, Object> data = orderService.selectOrderTotal(account.getAccountId());
        return ApiResult.ok(data);
    }*/

}