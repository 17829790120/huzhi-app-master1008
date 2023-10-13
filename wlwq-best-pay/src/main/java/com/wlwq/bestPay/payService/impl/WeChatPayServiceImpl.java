package com.wlwq.bestPay.payService.impl;

import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.date.DateUtil;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayOrderQueryV3Request;
import com.github.binarywang.wxpay.bean.request.WxPayRefundV3Request;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayRefundV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.github.binarywang.wxpay.v3.util.SignUtils;
import com.google.gson.Gson;
import com.wlwq.bestPay.constant.BestPayConstant;
import com.wlwq.bestPay.constant.WechatPayConstant;
import com.wlwq.bestPay.enums.BestPayTypeEnum;
import com.wlwq.bestPay.enums.WeChatPayEnum;
import com.wlwq.bestPay.params.PayRequest;
import com.wlwq.bestPay.params.QueryRequest;
import com.wlwq.bestPay.params.RefundRequest;
import com.wlwq.bestPay.pay.domain.PayConfig;
import com.wlwq.bestPay.pay.service.IPayConfigService;
import com.wlwq.bestPay.result.QueryOrderResult;
import com.wlwq.bestPay.result.RefundResult;
import com.wlwq.bestPay.result.WeChatAppPayResult;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.utils.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName WeChatPayServiceImpl
 * @Description 微信支付
 * @Date 2021/7/1 16:53
 * @Author Rick wlwq
 */
@Slf4j
public class WeChatPayServiceImpl extends BestPayServiceImpl {

    private static final ConverterRegistry CONVERTER_REGISTRY = ConverterRegistry.getInstance();

    /**
     * @Description 查询订单
     * @author Rick wlwq
     * @Date 2021/7/6 15:07
     */
    public QueryOrderResult query(QueryRequest queryRequest) {
        WxPayConfig payConfig = getWxPayConfig(queryRequest.getAppId());
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        WxPayOrderQueryV3Request request = new WxPayOrderQueryV3Request();
        // 商户号
        request.setMchid(payConfig.getMchId());
        // 支付交易号
        request.setTransactionId(queryRequest.getTradeNo());
        // 商户订单号
        request.setOutTradeNo(queryRequest.getOrderId());
        try {
            WxPayOrderQueryV3Result result = wxPayService.queryOrderV3(request);
            log.info("微信查询订单返回参数：{{}}", result.toString());
            return QueryOrderResult.builder()
                    .code(result.getTradeState())
                    .msg(result.getTradeStateDesc())
                    .data(WechatPayConstant.QUERY_ORDER_STATUS_IS_SUCCESS.equals(result.getTradeState()))
                    .build();
        } catch (WxPayException e) {
            log.error("微信查询订单失败！异常信息：{{}}", e.toString());
            throw new ApiException(e.getMessage());
        }
    }


    /**
     * @Description 退款
     * @author Rick wlwq
     * @Date 2021/7/6 11:15
     */
    public RefundResult refund(RefundRequest refundRequest) {
        WxPayConfig payConfig = getWxPayConfig(refundRequest.getAppId());
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        WxPayRefundV3Request request = new WxPayRefundV3Request();
        // 商户订单号
        if (StringUtils.isNotBlank(refundRequest.getOrderId())) {
            request.setOutTradeNo(refundRequest.getOrderId());
        }
        // 支付交易订单号
        if (StringUtils.isNotBlank(refundRequest.getTradeNo())) {
            request.setTransactionId(refundRequest.getTradeNo());
        }
        // 退款订单号
        request.setOutRefundNo(refundRequest.getRefundOrderId());
        // 退款原因
        request.setReason(refundRequest.getRefundReason());
        // 退款金额
        WxPayRefundV3Request.Amount amount = new WxPayRefundV3Request.Amount();
        amount.setTotal(BaseWxPayRequest.yuanToFen(CONVERTER_REGISTRY.convert(String.class, refundRequest.getTotalAmount())));
        amount.setRefund(BaseWxPayRequest.yuanToFen(CONVERTER_REGISTRY.convert(String.class, refundRequest.getRefundAmount())));
        amount.setCurrency(WeChatPayEnum.CURRENCY.getDesc());
        request.setAmount(amount);
        try {
            WxPayRefundV3Result result = wxPayService.refundV3(request);
            log.info("微信退款返回参数：{{}}", result.toString());
            switch (result.getStatus()) {
                // 退款成功
                case WechatPayConstant.REFUND_ORDER_STATUS_IS_SUCCESS:
                    return RefundResult.builder()
                            .code(BestPayConstant.REFUND_SUCCESS)
                            .message("退款成功！")
                            .build();
                case WechatPayConstant.REFUND_ORDER_STATUS_IS_CLOSED:
                    return RefundResult.builder()
                            .code(BestPayConstant.REFUND_ERROR)
                            .message("退款已关闭！")
                            .build();
                case WechatPayConstant.REFUND_ORDER_STATUS_IS_PROCESSING:
                    return RefundResult.builder()
                            .code(BestPayConstant.REFUND_SUCCESS)
                            .message("退款处理中！")
                            .build();
                default:
                    throw new ApiException("退款异常！");
            }
        } catch (WxPayException e) {
            log.error("微信退款失败！异常信息：{{}}", e.toString());
            throw new ApiException(e.getMessage());
        }
    }

    /**
     * @Description 微信拉起支付
     * @author Rick wlwq
     * @Date 2021/7/3 17:51
     */
    public String pay(PayRequest payRequest) {
        WxPayConfig payConfig = getWxPayConfig(payRequest.getAppId());
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        WxPayUnifiedOrderV3Request orderV3Request = new WxPayUnifiedOrderV3Request();
        // 订单描述
        orderV3Request.setDescription(payRequest.getOrderName());
        // 商户订单号
        orderV3Request.setOutTradeNo(payRequest.getOrderId());
        // 订单超时时间(RFC3339时间格式) yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
        orderV3Request.setTimeExpire(DateUtil.format(DateUtil.offsetMinute(DateUtil.date(), payRequest.getTimeOutValue()), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        // 回调
        orderV3Request.setNotifyUrl(payConfig.getNotifyUrl());
        // 附加值
        orderV3Request.setAttach(payRequest.getModuleName());
        WxPayUnifiedOrderV3Request.Amount amount = new WxPayUnifiedOrderV3Request.Amount();
        amount.setTotal(BaseWxPayRequest.yuanToFen(CONVERTER_REGISTRY.convert(String.class, payRequest.getOrderAmount())));
        amount.setCurrency(WeChatPayEnum.CURRENCY.getDesc());
        orderV3Request.setAmount(amount);

        try {

            if (BestPayTypeEnum.WXPAY_APP.getCode().equals(payRequest.getPayTypeEnum().getCode())) {
                WxPayUnifiedOrderV3Result.AppResult result = wxPayService.createOrderV3(getTradeTypeEnum(payRequest.getPayTypeEnum()), orderV3Request);
                log.info("微信创建订单返回参数：{{}}", result.toString());
                Gson gson = new Gson();
                return gson.toJson(WeChatAppPayResult.builder()
                        .appId(result.getAppid())
                        .partnerId(result.getPartnerid())
                        .prepayId(result.getPrepayid())
                        .packageValue(result.getPackageValue())
                        .nonceStr(result.getNoncestr())
                        .timestamp(result.getTimestamp())
                        // 需要生成签名
                        .sign(SignUtils.sign(payConfig.getAppId() + "\n" + result.getTimestamp() + "\n" + result.getNoncestr() + "\n" + result.getPrepayid() + "\n", payConfig.getPrivateKey()))
                        .build());
            }
            if(BestPayTypeEnum.WXPAY_MINI.getCode().equals(payRequest.getPayTypeEnum().getCode())) {
                if (StringUtils.isNotBlank(payRequest.getOpenid())) {
                    WxPayUnifiedOrderV3Request.Payer payer = new WxPayUnifiedOrderV3Request.Payer();
                    payer.setOpenid(payRequest.getOpenid());
                    orderV3Request.setPayer(payer);
                }
                WxPayUnifiedOrderV3Result.JsapiResult result = wxPayService.createOrderV3(getTradeTypeEnum(payRequest.getPayTypeEnum()), orderV3Request);
                log.info("微信小程序创建订单返回参数：{{}}", result.toString());
                Gson gson = new Gson();
                return gson.toJson(WeChatAppPayResult.builder()
                        .timeStamp(result.getTimeStamp())
                        .nonceStr(result.getNonceStr())
                        .packageValue(result.getPackageValue())
                        .signType(result.getSignType())
                        .paySign(result.getPaySign())
                        .build());
            }
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }


    /**
     * @Description 获取支付配置
     * @author Rick wlwq
     * @Date 2021/7/3 17:46
     */
    private WxPayConfig getWxPayConfig(String appId) {
        PayConfig config = SpringUtils.getBean(IPayConfigService.class).selectPayConfigByAppId(appId);
        if (StringUtils.isNull(config)) {
            throw new RuntimeException("未获取到配置信息！请检查配置后重试！");
        }
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(StringUtils.trimToNull(config.getAppId()));
        payConfig.setMchId(StringUtils.trimToNull(config.getMchId()));
        payConfig.setMchKey(StringUtils.trimToNull(config.getMchKey()));
        payConfig.setKeyPath(StringUtils.trimToNull(config.getKeyPath()));
        payConfig.setApiV3Key(StringUtils.trimToNull(config.getApiV3Key()));
        payConfig.setPrivateKeyPath(StringUtils.trimToNull(config.getApiclientKey()));
        payConfig.setPrivateCertPath(StringUtils.trimToNull(config.getApiclientCert()));
        payConfig.setCertSerialNo(StringUtils.trimToNull(config.getCertSerialNo()));
        payConfig.setNotifyUrl(StringUtils.trimToNull(config.getNotifyUrl() + BestPayConstant.NOTIFY_API_URL_WECHAT + config.getPayConfigId()));
        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(false);
        return payConfig;
    }

    /**
     * @Description 获取支付类型
     * @author Rick wlwq
     * @Date 2021/7/3 17:50
     */
    private TradeTypeEnum getTradeTypeEnum(BestPayTypeEnum payTypeEnum) {
        // 判断支付类型
        TradeTypeEnum tradeTypeEnum;
        if (payTypeEnum.equals(BestPayTypeEnum.WXPAY_APP)) {
            tradeTypeEnum = TradeTypeEnum.APP;
        } else if (payTypeEnum.equals(BestPayTypeEnum.WXPAY_MINI)) {
            tradeTypeEnum = TradeTypeEnum.JSAPI;
        } else if (payTypeEnum.equals(BestPayTypeEnum.WXPAY_NATIVE)) {
            tradeTypeEnum = TradeTypeEnum.NATIVE;
        } else {
            tradeTypeEnum = TradeTypeEnum.H5;
        }
        return tradeTypeEnum;
    }


}
