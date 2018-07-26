package com.wesley.study.attribute.alternate.exchange;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.wesley.study.client.utils.ChannelUtils;
import com.wesley.study.config.RabbitConstant;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by Wesley on 2018/7/26.
 */
public class MessageProducer {
    public static void main(String[] args) throws IOException {
        Channel channel = ChannelUtils.getChannelInstance("RGP订单系统消息生产者");

        // 声明AE 类型为Fanout
        channel.exchangeDeclare("wesley.order.failure", BuiltinExchangeType.FANOUT, true, false, false, new HashMap<String, Object>());

        /**
         * wesley.order设置AE
         * Alternate Exchange简称AE，当消息不能被正确路由时，如果交换机设置了AE则消息会被投递到AE中，
         * 如果存在AE链则会按此继续投递，直到消息被正确路由或AE链结束消息被丢弃。
         * 通常建议AE的交换机类型为Fanout防止出现路由失败，
         * 如果一个交换机指定了AE那么意为着该交换机和AE链都无法被正确路由时才会触发消息返回
         */
        Map<String, Object> exchangeProperties = new HashMap<>();
//        exchangeProperties.put("alternate-exchange", "wesley.order.failure");
        channel.exchangeDeclare(RabbitConstant.EXCHANGE_DIRECT_ORDER, BuiltinExchangeType.DIRECT, true, false, false, exchangeProperties);

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().deliveryMode(2).contentType("UTF-8").build();
        // 设置消息属性 发布消息 (交换机名, Routing key, 可靠消息相关属性 后续会介绍, 消息属性, 消息体);
        channel.basicPublish(RabbitConstant.EXCHANGE_DIRECT_ORDER, "addXXX", false, basicProperties, "订单信息".getBytes());
    }
}
