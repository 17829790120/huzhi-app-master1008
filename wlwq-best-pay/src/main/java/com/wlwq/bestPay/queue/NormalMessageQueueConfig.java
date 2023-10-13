package com.wlwq.bestPay.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 普通队列配置
 *
 * @author Chen Haoxuan
 * @date 2022/2/17 18:33
 */
@Configuration
@Slf4j
public class NormalMessageQueueConfig {
    /**
     * rabbitmq队列名字
     */
    public final static String NORMAL_QUEUE = "normal-queue-project";
    /**
     * 交换机
     */
    public final static String TOPIC_EXCHANGE = "normal-exchange-project";
    /**
     * 路由key
     */
    public final static String ROUTING_KEY = "normal-routing-key-project";

    /**
     * 定义交换机
     *
     * @return
     */
    @Bean
    public TopicExchange createNormalExchange() {
        log.info("创建交换机：{{}}", TOPIC_EXCHANGE);
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    /**
     * 定义回调消息队列
     */
    @Bean
    public Queue normalQueue() {
        log.info("创建队列：{{}}", NORMAL_QUEUE);
        return new Queue(NORMAL_QUEUE);
    }

    /**
     * 把队列 绑定到交换机里面指定的路由key
     * 解释: 将NORMAL_QUEUE绑定到TOPIC_EXCHANGE里指定的ROUTING_KEY
     *
     * @return 绑定之后的一个关系
     */
    @Bean
    public Binding bindingNormalQueue() {
        return BindingBuilder.bind(normalQueue()).to(createNormalExchange()).with(ROUTING_KEY);
    }
}
