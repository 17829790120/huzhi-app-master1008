package com.wlwq.bestPay.payService.impl;

import cn.hutool.core.convert.ConverterRegistry;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.wlwq.bestPay.enums.BestPayPlatformEnum;
import com.wlwq.bestPay.params.PayRequest;
import com.wlwq.bestPay.params.QueryRequest;
import com.wlwq.bestPay.params.RefundRequest;
import com.wlwq.bestPay.payService.BestPayService;
import com.wlwq.bestPay.result.QueryOrderResult;
import com.wlwq.bestPay.result.RefundResult;
import com.wlwq.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @ClassName BestPayServiceImpl
 * @Description 支付实现类
 * @Date 2021/7/1 16:52
 * @Author Rick wlwq
 */
@Slf4j
@Service
public class BestPayServiceImpl implements BestPayService {

    private static final ConverterRegistry CONVERTER_REGISTRY = ConverterRegistry.getInstance();

    /**
     * @Description 查询订单
     * @author Rick wlwq
     * @Date 2021/7/6 15:04
     */
    @Override
    public QueryOrderResult queryOrder(QueryRequest queryRequest) {
        Objects.requireNonNull(queryRequest, "查询订单请求参数不能是空！");
        log.info("查询订单请求参数：{{}}", queryRequest);
        if (StringUtils.isBlank(queryRequest.getOrderId()) && StringUtils.isBlank(queryRequest.getTradeNo())) {
            throw new RuntimeException("商户订单号和支付交易订单号必须传一个！");
        }
        //微信退款
        if (BestPayPlatformEnum.WX == queryRequest.getPayTypeEnum().getPlatform()) {
            WeChatPayServiceImpl wxPayService = new WeChatPayServiceImpl();
            return wxPayService.query(queryRequest);
        }
        // 支付宝支付
        else if (BestPayPlatformEnum.ALIPAY == queryRequest.getPayTypeEnum().getPlatform()) {
            AliPayServiceImpl aliPayService = new AliPayServiceImpl();
            return aliPayService.query(queryRequest);
        }
        throw new RuntimeException("错误的支付平台");
    }

    /**
     * @Description 退款
     * @author Rick wlwq
     * @Date 2021/7/6 15:04
     */
    @Override
    public RefundResult refundOrder(RefundRequest refundRequest) {
        Objects.requireNonNull(refundRequest, "退款请求参数不能是空！");
        log.info("退款请求参数：{{}}", refundRequest);
        if (StringUtils.isBlank(refundRequest.getOrderId()) && StringUtils.isBlank(refundRequest.getTradeNo())) {
            throw new RuntimeException("商户订单号和支付交易订单号必须传一个！");
        }
        Integer totalFee = BaseWxPayRequest.yuanToFen(CONVERTER_REGISTRY.convert(String.class, refundRequest.getTotalAmount()));
        Integer refundFee = BaseWxPayRequest.yuanToFen(CONVERTER_REGISTRY.convert(String.class, refundRequest.getRefundAmount()));
        // 退款金额大于订单金额
        if (refundFee > totalFee) {
            throw new RuntimeException("退款金额不能大于订单总金额！");
        }
        //微信退款
        if (BestPayPlatformEnum.WX == refundRequest.getPayTypeEnum().getPlatform()) {
            WeChatPayServiceImpl wxPayService = new WeChatPayServiceImpl();
            return wxPayService.refund(refundRequest);
        }
        // 支付宝支付
        else if (BestPayPlatformEnum.ALIPAY == refundRequest.getPayTypeEnum().getPlatform()) {
            AliPayServiceImpl aliPayService = new AliPayServiceImpl();
            return aliPayService.refund(refundRequest);
        }
        throw new RuntimeException("错误的支付平台");
    }

    /**
     * @Description 拉起支付
     * @author Rick wlwq
     * @Date 2021/7/1 17:25
     */
    @Override
    public String placeOrder(PayRequest payRequest) {
        Objects.requireNonNull(payRequest, "请求参数不能是空！");
        log.info("拉起支付请求参数：{{}}", payRequest);
        //微信支付
        if (BestPayPlatformEnum.WX == payRequest.getPayTypeEnum().getPlatform()) {
            WeChatPayServiceImpl wxPayService = new WeChatPayServiceImpl();
            return wxPayService.pay(payRequest);
        }
        // 支付宝支付
        else if (BestPayPlatformEnum.ALIPAY == payRequest.getPayTypeEnum().getPlatform()) {
            AliPayServiceImpl aliPayService = new AliPayServiceImpl();
            return aliPayService.pay(payRequest);
        }
        throw new RuntimeException("错误的支付平台");
    }
}
