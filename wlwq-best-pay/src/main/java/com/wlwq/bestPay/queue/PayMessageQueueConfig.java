package com.wlwq.bestPay.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 支付队列配置
 *
 * @author Chen Haoxuan
 * @date 2022/5/25
 */
@Configuration
@Slf4j
public class PayMessageQueueConfig {

    /**
     * rabbitmq队列名字
     */
    public final static String PAY_QUEUE = "pay-queue-project";
    /**
     * 交换机
     */
    public final static String PAY_EXCHANGE = "pay-exchange-project";
    /**
     * 路由key
     */
    public final static String PAY_ROUTING_KEY = "pay-project";

    /**
     * 定义交换机
     *
     * @return
     */
    @Bean
    public TopicExchange createPayExchange() {
        log.info("创建支付交换机：{{}}", PAY_EXCHANGE);
        return new TopicExchange(PAY_EXCHANGE);
    }

    /**
     * 定义回调消息队列
     */
    @Bean
    public Queue payQueue() {
        log.info("创建支付队列：{{}}", PAY_QUEUE);
        return new Queue(PAY_QUEUE);
    }

    /**
     * 把队列 绑定到交换机里面指定的路由key
     * 解释: 将NORMAL_QUEUE绑定到TOPIC_EXCHANGE里指定的ROUTING_KEY
     *
     * @return 绑定之后的一个关系
     */
    @Bean
    public Binding bindingPayQueue() {
        return BindingBuilder.bind(payQueue()).to(createPayExchange()).with(PAY_ROUTING_KEY);
    }
}
