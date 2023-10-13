package com.wlwq.bestPay.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.wlwq.bestPay.mq.DelayedMessageQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName RabbitSendService
 * @Description 队列发送消息服务
 * @Date 2021/11/2 11:39
 * @Author
 */
@Component
@Slf4j
public class RabbitSendService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @Description 发送rabbitmq消息
     * @author Rick Jen
     * @Date 2022/2/17 18:21
     * exchange 交换
     * routingKey 路由key
     * msg 消息 如果要传对象的话一般转为json字符串发送
     */
    public void sendMessage(String exchange, String routingKey, String msg) {
        // 发送MQ消息
        rabbitTemplate.convertAndSend(exchange, routingKey, msg,
                message -> {
                    // 生成消息唯一号
                    String messageId = "normal-" + IdUtil.getSnowflake(6, 6).nextIdStr();
                    // 自己给消息设置自定义的ID
                    message.getMessageProperties().setMessageId(messageId);
                    return message;
                });
    }

    /**
     * 发送延迟消息
     *
     * @param expiration 延时毫秒
     * @param msg        消息
     */
    public void sendDelayMessage(Long expiration, String msg) {
        sendDelayMessage(DelayedMessageQueueConfig.DELAYED_EXCHANGE, DelayedMessageQueueConfig.DELAYED_ROUTING_KEY, expiration, msg);
    }

    /**
     * @Description 发送延时消息（另一种方法）
     * @author Rick Jen
     * @Date 2021/12/17 15:53
     * @Param exchange 交换机名字
     * @Param routingKey 路由key
     * @Param expiration 延时毫秒
     * @Param msg 消息
     */
    public void sendDelayMessage(String exchange, String routingKey, Long expiration, String msg) {
        log.info("发送延时消息：{}，发送时间：{}，延时消费毫秒数：{}", msg, DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), expiration);
        // 发送MQ延时消息
        rabbitTemplate.convertAndSend(exchange, routingKey, msg, message -> {
            // 生成消息唯一号
            String messageId = "delay-" + IdUtil.getSnowflake(8, 8).nextIdStr();
            // 自己给消息设置自定义的ID
            message.getMessageProperties().setMessageId(messageId);
            //注意这里时间可以使long，而且是设置header
//            message.getMessageProperties().setHeader("x-delay",expiration);
            message.getMessageProperties().setDelay(Math.toIntExact(expiration));
            return message;
        });
    }

}
