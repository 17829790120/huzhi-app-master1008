package com.wlwq.notify;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.date.DateUtil;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.wlwq.api.domain.GoodsOrder;
import com.wlwq.api.service.IGoodsOrderService;
import com.wlwq.bestPay.constant.ModuleConstant;
import com.wlwq.bestPay.mq.RabbitMQSendService;
import com.wlwq.bestPay.mq.RabbitMqService;
import com.wlwq.bestPay.result.NotifyResult;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName PayNotifyListener
 * @Description RabbitMQ监听支付回调消息
 * @Date 2021/1/27 16:41
 * @Author Rick wlwq
 */
@Component
@RabbitListener(queues = RabbitMqService.QUEUE_PAY_NOTIFY)
@Slf4j
public class PayNotifyListener {

    @Autowired
    private BitMapBloomFilter bitMapBloomFilter;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private IGoodsOrderService orderService;


    /**
     * 消息的前缀
     */
    private final String MESSAGE = "project-message:";

    @RabbitHandler
    public void process(String msg, Message message, Channel channel) throws Exception {
        log.info("【接收到消息】=> {}", msg);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String messageId = message.getMessageProperties().getMessageId();
        log.info("消息投递ID → ：{}", deliveryTag);
        log.info("消息自定义ID → ：{}", messageId);
        if (StringUtils.isNotBlank(messageId)) {
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
        // 签收消息
        channel.basicAck(deliveryTag, true);
        log.info("消息签收成功");
        // 消费成功之后放到布隆过滤器里面
        this.bitMapBloomFilter.add(messageId);
        // 执行逻辑
        NotifyResult notify = new Gson().fromJson(msg, NotifyResult.class);
        // 商品支付
        if (notify.getModuleName().equals(ModuleConstant.GOOD_ORDER_MODULE)) {
            log.info("接收到RabbitMQ的支付成功消息：模块为{{}}", ModuleConstant.GOOD_ORDER_MODULE);
            dealGoodOrder(notify);
        }
    }

    /**
     * 商品及购物车下单回调处理
     *
     * @param notify
     */
    @Transactional(rollbackFor = Exception.class)
    public void dealGoodOrder(NotifyResult notify) {
        List<GoodsOrder> orderList = orderService.selectGoodsOrderList(GoodsOrder.builder().orderSn(notify.getOrderId()).build());
        for (GoodsOrder order : orderList) {
            int count = orderService.updateGoodsOrder(GoodsOrder.builder()
                    .orderId(order.getOrderId())
                    .payStatus(1)
                    //订单状态（0：待付款 1：待发货 2：待收货 3：已完成  4：退款/售后  5：已取消 6：已关闭）
                    .orderStatus("3")
                    .tradeNo(notify.getTradeNo())
                    .payTime(DateUtil.date())
                    .build());
            if (count < 1) {
                throw new ApiException("商品及购物车下单支付回调失败！");
            }
        }
    }

}
