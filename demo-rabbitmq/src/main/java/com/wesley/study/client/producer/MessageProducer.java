package com.wesley.study.client.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.wesley.study.client.utils.ChannelUtils;
import java.io.IOException;
import java.util.HashMap;

/**
 * 消息生产者
 * @author Created by Wesley on 2018/7/19.
 */
public class MessageProducer {

    public static void main(String[] args) throws IOException {
        Channel channel = ChannelUtils.getChannelInstance("RGP订单系统消息生产者");

        // 声明交换机 (交换机名, 交换机类型, 是否持久化, 是否自动删除, 是否是内部交换机, 交换机属性);
        channel.exchangeDeclare("wesley.order", BuiltinExchangeType.DIRECT, true, false, false, new HashMap<>());

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().deliveryMode(2).contentType("UTF-8").build();
        // 设置消息属性 发布消息 (交换机名, Routing key, 可靠消息相关属性 后续会介绍, 消息属性, 消息体);
        channel.basicPublish("wesley.order", "add", false, basicProperties, "订单信息".getBytes());
    }
}
