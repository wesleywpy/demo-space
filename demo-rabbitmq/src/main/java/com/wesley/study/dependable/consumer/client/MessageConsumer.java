package com.wesley.study.dependable.consumer.client;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.wesley.study.client.utils.ChannelUtils;
import com.wesley.study.config.RabbitConstant;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Created by Wesley on 2018/7/26.
 */
public class MessageConsumer {

    public static void main(String[] args) throws IOException {
        Channel channel = ChannelUtils.getChannelInstance("RGP订单系统消息消费者");
        // 声明队列 (队列名, 是否持久化, 是否排他, 是否自动删除, 队列属性);
        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(RabbitConstant.QUEUE_ORDER_ADD, true, false, false, new HashMap<>());

        // 声明交换机 (交换机名, 交换机类型, 是否持久化, 是否自动删除, 是否是内部交换机, 交换机属性);
        channel.exchangeDeclare(RabbitConstant.EXCHANGE_DIRECT_ORDER, BuiltinExchangeType.DIRECT, true, false, false, new HashMap<>());

        // 将队列Binding到交换机上 (队列名, 交换机名, Routing key, 绑定属性);
        channel.queueBind(declareOk.getQueue(), RabbitConstant.EXCHANGE_DIRECT_ORDER, "add", new HashMap<>());

        /**
         * 消息自动确认设置为false，在消费消息的时候手动使用channel.basicAck()进行消息确认，channel.basicNack()进行消息拒绝
         */
        channel.basicConsume(declareOk.getQueue(), false, "RGP订单系统ADD处理逻辑消费者", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    System.out.println(consumerTag);
                    System.out.println(envelope.toString());
                    System.out.println(properties.toString());
                    System.out.println("消息内容:" + new String(body));
                    if ("订单信息2".equals(new String(body))) {
                        throw new RuntimeException();
                    } else {
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                } catch (Exception e) {
                    channel.basicNack(envelope.getDeliveryTag(), false, true);
                }
            }
        });

    }
}
