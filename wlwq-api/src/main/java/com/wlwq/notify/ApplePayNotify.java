package com.wlwq.notify;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.GoodsOrder;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.IGoodsOrderService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.IosVerifyUtil;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @Author:gaoce
 * @Date:2021/5/11 14:38
 */
@RestController
@RequestMapping(value = "/api/applePay")
@AllArgsConstructor
public class ApplePayNotify extends ApiController {

    private final IApiAccountService accountService;

    private final IGoodsOrderService goodsOrderService;


    /**
     * 资源文件（苹果支付成功回调）
     * orderId 订单ID
     * TransactionID 流水号
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/buyResourceFile")
    public ApiResult buyResourceFile(HttpServletRequest request, String orderId, String TransactionID, String Payload) {
        // 查询订单信息
        GoodsOrder goodsOrder = goodsOrderService.selectGoodsOrderById(orderId);
        if (goodsOrder == null) {
            throw new ApiException("未获取到订单信息！");
        }
        // 1.先线上测试 发送平台验证
        String verifyResult = IosVerifyUtil.buyAppVerify(Payload, 1);
        // 苹果服务器没有返回验证结果
        if (verifyResult == null) {
            throw new ApiException("无订单信息");
        }
        // 苹果验证有返回结果
        System.out.println("线上，苹果平台返回JSON:" + verifyResult);
        JSONObject verifyResultJSON = JSONObject.parseObject(verifyResult);
        if (verifyResultJSON.getInteger("status") == 21007) {
            verifyResult = IosVerifyUtil.buyAppVerify(Payload, 0);
            // 苹果验证有返回结果
            System.out.println("沙盒，苹果平台返回JSON:" + verifyResult);
        }
        verifyResultJSON = JSONObject.parseObject(verifyResult);
        if (verifyResultJSON.getInteger("status") != 0) {
            throw new ApiException("苹果验证订单失败！");
        }
        // 苹果支付收据
        if (!verifyResultJSON.containsKey("receipt")) {
            throw new ApiException("苹果验证订单失败！");
        }
        JSONObject receipt = verifyResultJSON.getJSONObject("receipt");
        // APP中数据
        if (!receipt.containsKey("in_app")) {
            throw new ApiException("苹果验证订单失败！");
        }
        JSONArray inApp = receipt.getJSONArray("in_app");
        if (inApp.size() <= 0) {
            throw new ApiException("苹果验证订单失败！");
        }
        if (!inApp.getJSONObject(0).containsKey("in_app_ownership_type")) {
            throw new ApiException("苹果验证订单失败！");
        }
        // 购买结果
        String inAppOwnershipType = inApp.getJSONObject(0).getString("in_app_ownership_type");
        if (!inAppOwnershipType.toUpperCase(Locale.ROOT).equals("PURCHASED")) {
            throw new ApiException("苹果验证订单失败！");
        }
        if (goodsOrder.getPayStatus() == 0) {
            int count = goodsOrderService.updateGoodsOrder(GoodsOrder.builder()
                    .orderId(orderId)
                    .payStatus(1)
                    //订单状态（0：待付款 1：待发货 2：待收货 3：已完成  4：退款/售后  5：已取消 6：已关闭）
                    .orderStatus("3")
                    .tradeNo(TransactionID)
                    .payTime(DateUtil.date())
                    .build());
            if (count < 1) {
                throw new ApiException("商品下单支付苹果回调失败！");
            }
        }
        return ok("购买成功！");
    }
}
