package com.wlwq.bestPay.notifyController;

import cn.hutool.core.convert.ConverterRegistry;
import com.github.binarywang.wxpay.bean.notify.OriginNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.google.gson.Gson;
import com.wlwq.bestPay.constant.BestPayConstant;
import com.wlwq.bestPay.constant.WechatPayConstant;
import com.wlwq.bestPay.mq.RabbitMqService;
import com.wlwq.bestPay.pay.domain.PayConfig;
import com.wlwq.bestPay.pay.service.IPayConfigService;
import com.wlwq.bestPay.result.NotifyResult;
import com.wlwq.bestPay.service.RabbitSendService;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.utils.spring.SpringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * @ClassName WechatPayNotifyController
 * @Description 微信支付回调
 * @Date 2021/1/27 17:11
 * @Author Rick wlwq
 */
@RestController
@RequestMapping(value = "/pay/wechatPay")
@Slf4j
@AllArgsConstructor
public class WechatPayNotifyController {

    private static final ConverterRegistry CONVERTER_REGISTRY = ConverterRegistry.getInstance();

    private final RabbitSendService rabbitSendService;

    /**
     * 微信支付回调
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/notifyWxPay/{configId}")
    public String notifyWxPay(@PathVariable("configId") String configId, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("微信支付回调获取到configId:{{}}", configId);
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            // 获取支付配置
            WxPayConfig wxPayConfig = getWxPayConfig(configId);
            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(wxPayConfig);
            WxPayOrderNotifyV3Result notifyResult = wxPayService.parseOrderNotifyV3Result(xmlResult, null);
            OriginNotifyResponse notifyResponse = notifyResult.getRawData();
            if (notifyResponse.getEventType().equals(WechatPayConstant.EVENT_TYPE_SUCCESS)) {
                WxPayOrderNotifyV3Result.DecryptNotifyResult decryptNotifyResult = notifyResult.getResult();
                // 结果正确 outTradeNo
                String orderSn = decryptNotifyResult.getOutTradeNo();
                String tradeNo = decryptNotifyResult.getTransactionId();
                String attach = decryptNotifyResult.getAttach();
                String totalFee = BaseWxPayResult.fenToYuan(decryptNotifyResult.getAmount().getTotal());
                // 支付成功->执行操作
                log.info("商户生成的订单流水号：{{}}", orderSn);
                log.info("微信支付流水号：{{}}", tradeNo);
                log.info("支付金额/元：{{}}", totalFee);
                log.info("附加信息：{{}}", attach);
                log.info("支付成功->执行操作");
                // 组装发送消息参数
                NotifyResult notify = NotifyResult.builder()
                        .orderId(orderSn)
                        .tradeNo(tradeNo)
                        .totalAmount(CONVERTER_REGISTRY.convert(BigDecimal.class, totalFee))
                        .buyerLogonId("0.0.0.0")
                        .moduleName(attach)
                        .build();
                // 发送MQ消息
                rabbitSendService.sendMessage(RabbitMqService.TOPIC_EXCHANGE, RabbitMqService.ROUTING_KEY,new Gson().toJson(notify));
                //自己处理订单的业务逻辑，需要判断订单是否已经支付过，否则可能会重复调用
                return WxPayNotifyResponse.success("服务器执行微信支付回调结果成功！");
            }
            return WxPayNotifyResponse.fail("code:" + -1 + "微信回调结果异常,异常原因:event_type值为" + notifyResponse.getEventType());
        } catch (Exception e) {
            log.error("微信回调结果异常,异常原因{{}}", e.getMessage());
            return WxPayNotifyResponse.fail("code:" + 9999 + "微信回调结果异常,异常原因:" + e.getMessage());
        }
    }

    /**
     * @Description 获取支付配置
     * @author Rick wlwq
     * @Date 2021/7/3 17:46
     */
    private WxPayConfig getWxPayConfig(String configId) {
        PayConfig config = SpringUtils.getBean(IPayConfigService.class).selectPayConfigById(configId);
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

}
