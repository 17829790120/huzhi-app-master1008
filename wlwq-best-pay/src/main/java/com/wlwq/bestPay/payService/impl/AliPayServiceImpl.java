package com.wlwq.bestPay.payService.impl;

import cn.hutool.core.convert.ConverterRegistry;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.wlwq.bestPay.constant.AliPayConstant;
import com.wlwq.bestPay.constant.BestPayConstant;
import com.wlwq.bestPay.enums.AlipayQueryStatusEnum;
import com.wlwq.bestPay.params.PayRequest;
import com.wlwq.bestPay.params.QueryRequest;
import com.wlwq.bestPay.params.RefundRequest;
import com.wlwq.bestPay.pay.domain.PayConfig;
import com.wlwq.bestPay.pay.service.IPayConfigService;
import com.wlwq.bestPay.result.QueryOrderResult;
import com.wlwq.bestPay.result.RefundResult;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.utils.spring.SpringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @ClassName AliPayServiceImpl
 * @Description 支付宝支付
 * @Date 2021/7/1 16:53
 * @Author Rick wlwq
 */
@Slf4j
public class AliPayServiceImpl extends BestPayServiceImpl {

    private static final ConverterRegistry CONVERTER_REGISTRY = ConverterRegistry.getInstance();

    /**
     * @Description 查询订单
     * @author Rick wlwq
     * @Date 2021/7/6 17:57
     */
    public QueryOrderResult query(QueryRequest queryRequest) {
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setTradeNo(queryRequest.getTradeNo());
        model.setOutTradeNo(queryRequest.getOrderId());
        alipayRequest.setBizModel(model);
        try {
            // 构造client
            AlipayClient alipayClient = getAliPayConfig(queryRequest.getAppId()).getAlipayClient();
            AlipayTradeQueryResponse response = alipayClient.certificateExecute(alipayRequest);
            System.out.println(response.getTradeStatus());
            // 若订单状态为成功
            if (response.getTradeStatus().equals(AliPayConstant.QUERY_ORDER_STATUS_IS_SUCCESS)) {
                return QueryOrderResult.builder()
                        .code(response.getTradeStatus())
                        .msg(AlipayQueryStatusEnum.findByName(response.getTradeStatus()).getDesc())
                        .data(true)
                        .build();
            }
            // 若订单状态不为成功
            return QueryOrderResult.builder()
                    .code(response.getTradeStatus())
                    .msg(AlipayQueryStatusEnum.findByName(response.getTradeStatus()).getDesc())
                    .data(false)
                    .build();
        } catch (AlipayApiException e) {
            log.error("支付宝查询订单异常！异常信息：{{}}", e.getMessage());
            e.printStackTrace();
            throw new ApiException("支付宝查询订单异常！");
        }
    }

    /**
     * @Description 退款
     * @author Rick wlwq
     * @Date 2021/7/6 17:50
     */
    public RefundResult refund(RefundRequest refundRequest) {
        AlipayTradeRefundRequest alipayTradeRefundRequest = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(refundRequest.getOrderId());
        model.setTradeNo(refundRequest.getTradeNo());
        model.setOperatorId(refundRequest.getOpUserId());
        // 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
        model.setOutRequestNo(refundRequest.getRefundOrderId());
        model.setRefundAmount(CONVERTER_REGISTRY.convert(String.class, refundRequest.getRefundAmount()));
        model.setRefundReason(refundRequest.getRefundReason());
        alipayTradeRefundRequest.setBizModel(model);
        try {
            // 构造client
            AlipayClient alipayClient = getAliPayConfig(refundRequest.getAppId()).getAlipayClient();
            AlipayTradeRefundResponse response = alipayClient.certificateExecute(alipayTradeRefundRequest);
            log.info("支付宝退款返回参数：{{}}", response);
            if (response.isSuccess() && response.getFundChange().equalsIgnoreCase(AliPayConstant.SUCCESS)) {
                // 成功
                return RefundResult.builder()
                        .code(BestPayConstant.REFUND_SUCCESS)
                        .message("退款成功！")
                        .build();
            } else {
                log.error("退款失败！返回参数：{{}}", response);
                // 失败
                return RefundResult.builder()
                        .code(BestPayConstant.REFUND_ERROR)
                        .message("退款失败！")
                        .build();
            }
        } catch (AlipayApiException e) {
            log.error("支付宝退款异常！异常信息：{{}}", e.getMessage());
            e.printStackTrace();
            // 失败
            throw new ApiException(e.getMessage());
        }
    }


    /**
     * @Description 拉起支付
     * @author Rick wlwq
     * @Date 2021/7/6 16:09
     */
    public String pay(PayRequest payRequest) {
        PayConfigResult config = getAliPayConfig(payRequest.getAppId());
        // 构造client
        AlipayClient alipayClient = config.getAlipayClient();
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setBizModel(
                createAlipayModel(
                        // 订单号
                        payRequest.getOrderId(),
                        // 订单金额
                        payRequest.getOrderAmount(),
                        // 交易描述
                        payRequest.getOrderName(),
                        // 商品名字
                        payRequest.getOrderName(),
                        // 附加值
                        payRequest.getModuleName(),
                        // 该笔订单允许的最晚付款时间 单位：分
                        payRequest.getTimeOutValue()));
        request.setNotifyUrl(config.getNotifyUrl());
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            //就是orderString 可以直接给客户端请求，无需再做处理。
            log.info("调用支付宝统一下单返回值：{{}}", response.getBody());
            return response.getBody();
        } catch (AlipayApiException e) {
            log.error("支付宝拉起支付异常！异常信息：{{}}", e.getMessage());
            e.printStackTrace();
            throw new ApiException("请求支付宝服务异常！");
        }
    }


    /**
     * 创建支付宝支付请求体
     *
     * @return
     * @Param orderId 订单号 建议雪花算法生成
     * @Param amount 订单金额
     * @Param transactionDescription 交易描述
     * @Param goodsName 商品标题
     */
    private AlipayTradeAppPayModel createAlipayModel(String orderId, BigDecimal amount, String transactionDescription, String goodsName, String moduleName, Integer timeOutValue) {
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        // 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
        model.setBody(transactionDescription);
        // 商品的标题/交易标题/订单标题/订单关键字等。
        model.setSubject(goodsName);
        // 商户网站唯一订单号  建议使用雪花算法生成
        model.setOutTradeNo(orderId);
        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：5m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        model.setTimeoutExpress(timeOutValue + "m");
        // 支付金额
        model.setTotalAmount(CONVERTER_REGISTRY.convert(String.class, amount));
        // 非必填参数 商户和支付宝签署的产品码 这个就不要传了
        // model.setProductCode("QUICK_MSECURITY_PAY");
        // 公用回传参数 这边传的模块名字
        model.setPassbackParams(moduleName);
        return model;
    }

    /**
     * @Description 获取支付配置信息
     * @author Rick wlwq
     * @Date 2021/7/6 16:48
     */
    private PayConfigResult getAliPayConfig(String appId) {
        PayConfig config = SpringUtils.getBean(IPayConfigService.class).selectPayConfigByAppId(appId);
        if (StringUtils.isNull(config)) {
            throw new RuntimeException("未获取到配置信息！请检查配置后重试！");
        }
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setAppId(StringUtils.trimToNull(config.getAppId()));
        //设置网关地址
        certAlipayRequest.setServerUrl(AliPayConstant.SERVER_API_URL);
        //设置应用私钥
        certAlipayRequest.setPrivateKey(config.getPrivateKey());
        //设置请求格式，固定值json
        certAlipayRequest.setFormat(AliPayConstant.FORMAT);
        //设置字符集
        certAlipayRequest.setCharset(AliPayConstant.CHARSET);
        //设置签名类型
        certAlipayRequest.setSignType(AliPayConstant.SIGN_TYPE);
        //设置应用公钥证书路径
        certAlipayRequest.setCertPath(config.getCertPath());
        //设置支付宝公钥证书路径
        certAlipayRequest.setAlipayPublicCertPath(config.getPublicCertPath());
        //设置支付宝根证书路径
        certAlipayRequest.setRootCertPath(config.getRootCertPath());
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
            return PayConfigResult.builder()
                    .notifyUrl(config.getNotifyUrl() + BestPayConstant.NOTIFY_API_URL_ALIPAY + config.getPayConfigId())
                    .alipayClient(alipayClient)
                    .build();
        } catch (AlipayApiException e) {
            log.error("构造AliPayClient异常！异常信息{{}}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}

/**
 * @author Rick wlwq
 * @Description 支付配置返回值封装
 * @Date 2021/7/6 16:46
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
class PayConfigResult {

    /**
     * 回调地址.
     */
    private String notifyUrl;

    /**
     * 阿里云RSA2模式请求参数.
     */
    private AlipayClient alipayClient;

}
