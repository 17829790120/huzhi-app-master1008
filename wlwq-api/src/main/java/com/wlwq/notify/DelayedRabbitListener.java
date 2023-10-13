package com.wlwq.notify;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.wlwq.api.domain.GoodsOrder;
import com.wlwq.api.service.IGoodsOrderService;
import com.wlwq.bestPay.constant.ModuleConstant;
import com.wlwq.bestPay.mq.DelayedMessageQueueConfig;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.dto.DelayedMessageDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;

/**
 * RabbitMQ监听延时队列
 *
 * @author baicc
 * @date 2022/06/30
 */
@Component
@Slf4j
@AllArgsConstructor
public class DelayedRabbitListener {

    private final BitMapBloomFilter bitMapBloomFilter;

    private final StringRedisTemplate redisTemplate;

    @Autowired
    private IGoodsOrderService orderService;

    /**
     * 消息的前缀
     */
    private final static String MESSAGE = "delayed-message:";

    @RabbitListener(queues = DelayedMessageQueueConfig.DELAYED_QUEUE)
    @Transactional(rollbackFor = Exception.class)
    public void process(String msg, Message message, Channel channel) throws IOException {
        log.info("【订单未支付-接收到消息，接受时间】" + DateUtils.getTime() + "=> {}", msg);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String messageId = message.getMessageProperties().getMessageId();
        log.info("消息投递ID → ：{}", deliveryTag);
        log.info("消息自定义ID → ：{}", messageId);
        if(StringUtils.isNotBlank(messageId)){
            if (this.bitMapBloomFilter.contains(messageId)) {
                log.error("message:{},该消息被消费过，不能重复进行消费", msg);
                try {
                    // 如果进入到这里面，说明这个消息之前被消费过，但是 MQ 认为你没有消费，所以我们要签收这条消息
                    channel.basicAck(deliveryTag, false);
                    return;
                } catch (Exception e) {
                    log.error("签收消息异常！异常原因：{}", e.toString());
                }
            }
        }

        // 消息被消费次数统计
        String count = this.redisTemplate.opsForValue().get(MESSAGE + messageId);
        if (count != null && Long.parseLong(count) >= 3) {
            channel.basicNack(deliveryTag, false, false);
            // 失败次数过多，这里可能需要人工介入
            log.error("该消息：{} ->消费【3】次都失败了！", message);
        }
        // 签收消息  最后再签收消息
        channel.basicAck(deliveryTag, true);
        log.info("消息签收成功");
        // 消费成功之后放到布隆过滤器里面
        this.bitMapBloomFilter.add(messageId);

        DelayedMessageDTO dto = new Gson().fromJson(msg, DelayedMessageDTO.class);

        // 商品订单支付超时
        if (dto.getModuleName().equals(ModuleConstant.GOOD_ORDER_PAY_TIMEOUT)) {
            log.info("商品订单支付超时！");
            goodsOrderOverTime(dto.getMessageContent());
        }

    }

    /**
     * 商品订单支付超时
     *
     * @param orderId 商品订单ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void goodsOrderOverTime(String orderId) {
        GoodsOrder order = orderService.selectGoodsOrderById(orderId);
        //是否支付
        if (order != null && "0".equals(order.getOrderStatus())) {
            // 支付关闭
            int result = orderService.updateGoodsOrder(GoodsOrder.builder()
                    .orderId(orderId)
                    .orderStatus("6")
                    .build());
            if (result < 1) {
                throw new ApiException("订单关闭失败！");
            }
        }
    }

}