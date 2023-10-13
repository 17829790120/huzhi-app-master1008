package com.wlwq.bestPay.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 延时队列配置
 *
 * @author baicc
 * @date 2022/07/20
 */
@Configuration
@Slf4j
public class DelayedMessageQueueConfig {

    /**
     * 逾期延时队列
     */
    public final static String DELAYED_QUEUE = "delayed-queue-huzhi";

    /**
     * 延时交换机
     */
    public final static String DELAYED_EXCHANGE = "delayed-exchange-huzhi";

    /**
     * 延时路由key
     */
    public final static String DELAYED_ROUTING_KEY = "delayed-routing-key-huzhi";

    /**
     * 延时队列交换机
     * 注意这里的交换机类型：CustomExchange
     */
    @Bean
    public CustomExchange createDelayExchange() {
        log.info("延时队列交换机创建：{}", DELAYED_EXCHANGE);
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", true, false, args);
    }

    /**
     * 延时队列
     */
    @Bean
    public Queue delayQueue() {
        log.info("延时队列创建：{}", DELAYED_QUEUE);
        return new Queue(DELAYED_QUEUE, true);
    }

    /**
     * 给延时队列绑定交换机
     */
    @Bean
    public Binding bindingQueue() {
        return BindingBuilder.bind(delayQueue()).to(createDelayExchange()).with(DELAYED_ROUTING_KEY).noargs();
    }

}
