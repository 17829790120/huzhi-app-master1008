package com.wlwq.bestPay.notifyController;

import cn.hutool.core.convert.ConverterRegistry;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.google.gson.Gson;
import com.wlwq.bestPay.constant.AliPayConstant;
import com.wlwq.bestPay.mq.RabbitMqService;
import com.wlwq.bestPay.pay.domain.PayConfig;
import com.wlwq.bestPay.pay.service.IPayConfigService;
import com.wlwq.bestPay.result.NotifyResult;
import com.wlwq.bestPay.service.RabbitSendService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName AliPayNotifyController
 * @Description 阿里云支付回调
 * @Date 2021/1/27 14:01
 * @Author Rick wlwq
 */
@RestController
@RequestMapping(value = "/pay/alipay")
@Slf4j
@AllArgsConstructor
public class AliPayNotifyController extends ApiController {

    private static final ConverterRegistry CONVERTER_REGISTRY = ConverterRegistry.getInstance();

    private final RabbitSendService rabbitSendService;
    private final IPayConfigService payConfigService;

    /**
     * @Description 阿里云支付回调
     * @author Rick wlwq
     * @Date 2021/1/27 14:22
     */
    @RequestMapping(value = "/notifyAliPay/{configId}", method = RequestMethod.POST)
    public String notifyAliPay(@PathVariable("configId") String configId, HttpServletRequest request) throws FileNotFoundException {
        log.info("支付宝支付回调获取到payConfigId:{{}}", configId);
        // 获取支付配置信息
        PayConfig payConfig = payConfigService.selectPayConfigById(configId);
        if (StringUtils.isNull(payConfig)) {
            throw new RuntimeException("未获取到支付宝配置信息！配置标识：" + configId);
        }
        // 一定要验签，防止黑客篡改参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder notifyBuild = new StringBuilder("/****************************** 支付宝支付回调 ******************************/\n");
        parameterMap.forEach((key, value) -> notifyBuild.append(key).append("=").append(value[0]).append("\n"));
        log.info(notifyBuild.toString());
        log.info("通知回调：AlipayController-notify-日志信息信息：" + request.getParameterMap().toString());

        boolean flag = rsaCheckV1(payConfig, request);
        if (flag) {
            /*
             * 商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号， 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）， 同时需要校验通知中的seller_id（或者seller_email)
             * 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
             *
             * 上述有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
             * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
             */
            // 交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // 商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // 支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // 付款金额
            String totalAmount = new String(request.getParameter("total_amount").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            String buyerLogonId = new String(request.getParameter("buyer_logon_id").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // 公用回传参数
            String passBackParams = new String(request.getParameter("passback_params").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // TRADE_FINISHED(表示交易已经成功结束，并不能再对该交易做后续操作);
            // TRADE_SUCCESS(表示交易已经成功结束，可以对该交易做后续操作，如：分润、退款等);
            if (AliPayConstant.TRADE_SUCCESS.equals(tradeStatus)) {
                // 交易成功->执行操作
                log.info("交易状态:{{}}", tradeStatus);
                log.info("商户订单号:{{}}", outTradeNo);
                log.info("支付宝交易号:{{}}", tradeNo);
                log.info("付款金额:{{}}", totalAmount);
                log.info("buyerLogonId:{{}}", buyerLogonId);
                log.info("公用回传参数:{{}}", passBackParams);
                // 组装发送消息参数
                NotifyResult notify = NotifyResult.builder()
                        .orderId(outTradeNo)
                        .tradeNo(tradeNo)
                        .totalAmount(CONVERTER_REGISTRY.convert(BigDecimal.class, totalAmount))
                        .buyerLogonId(buyerLogonId)
                        .moduleName(passBackParams)
                        .build();
                // 发送MQ消息
                log.debug("发送rabbitmq信息：{{}}", new Gson().toJson(notify));
                // 发送MQ消息
                rabbitSendService.sendMessage(RabbitMqService.TOPIC_EXCHANGE, RabbitMqService.ROUTING_KEY,new Gson().toJson(notify));
                // 返回success 让支付宝官方不再发送回调请求
                return "success";
            }
            return "failure";
        }
        // 验签失败 可发送短信或通知给相关人员
        log.error("验签失败！");
        return "failure";
    }


    /**
     * 校验签名 证书模式
     *
     * @param request
     * @return
     */
    public boolean rsaCheckV1(PayConfig payConfig, HttpServletRequest request) throws FileNotFoundException {
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。  注：未出现乱码注释掉 不然会报验签失败！
            // valueStr = new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            params.put(name, valueStr);
        }
        // 切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        // boolean AlipaySignature.rsaCertCheckV1(Map<String, String> params, String publicKeyCertPath, String charset,String signType)
        try {
            return AlipaySignature.rsaCertCheckV1(params, payConfig.getPublicCertPath(), AliPayConstant.CHARSET, AliPayConstant.SIGN_TYPE);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return false;
        }
    }

}
