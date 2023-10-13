package com.wlwq.controller.pay;

import com.alibaba.fastjson.JSONObject;
import com.wlwq.annotation.PassToken;
import com.wlwq.bestPay.enums.BestPayTypeEnum;
import com.wlwq.bestPay.mq.RabbitMQSendService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.system.service.ISysConfigService;
import lombok.AllArgsConstructor;
import com.wlwq.bestPay.constant.ModuleConstant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 延迟队列处理数据
 * @author wlwq
 */
@RequestMapping(value = "/api/delayed")
@RestController
@AllArgsConstructor
public class DelayedController extends ApiController {

    private final ISysConfigService sysConfigService;

    private final RabbitMQSendService rabbitSendService;

    /**
     * 延迟队列发送
     * @param bestPayTypeEnum
     * @return
     */
    @RequestMapping(value = "/toDelayed", method = RequestMethod.POST)
    @PassToken
    public void toDelayed(BestPayTypeEnum bestPayTypeEnum) {
        // 发送到消息队列（15分钟后还未完成支付，关闭订单）
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("moduleName", ModuleConstant.STORE_ORDER_PAY_TIMEOUT);
        // messageContent 订单ID
        jsonObject.put("messageContent", "12316541567845");
        // 商品订单未支付自动取消时间
        String configValue = sysConfigService.selectConfigByKey("good_order_pay_timeout");
        long payTimeout = StringUtils.isNotEmpty(configValue) ? Long.parseLong(configValue) : 15L;
        rabbitSendService.sendDelayMessage(payTimeout * 60 * 1000, jsonObject.toString());
    }
}
