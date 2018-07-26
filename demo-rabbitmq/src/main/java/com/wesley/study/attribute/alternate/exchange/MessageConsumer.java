package com.wesley.study.attribute.alternate.exchange;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.wesley.study.client.utils.ChannelUtils;
import com.wesley.study.config.RabbitConstant;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 消费者
 * @author Created by Wesley on 2018/7/19.
 */
public class MessageConsumer {

    public static void main(String[] args) throws IOException {
        Channel channel = ChannelUtils.getChannelInstance("RGP订单系统消息消费者");

        String orderFailureExchange = "wesley.order.failure";
        channel.exchangeDeclare(orderFailureExchange, BuiltinExchangeType.FANOUT, true, false, false, new HashMap<>());
        AMQP.Queue.DeclareOk declareOk2 = channel.queueDeclare("wesley.order.add.failure", true, false, false, new HashMap<>());
        // 将roberto.order.add.failure队列绑定到roberto.order.failure交换机上 无需指定routing key
        channel.queueBind(declareOk2.getQueue(), orderFailureExchange, "", new HashMap<>());


        // wesley.order设置AE
        Map<String, Object> exchangeProperties = new HashMap<>();
        exchangeProperties.put("alternate-exchange", orderFailureExchange);
        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(RabbitConstant.EXCHANGE_DIRECT_ORDER, true, false, false, exchangeProperties);
        channel.queueBind(declareOk.getQueue(), RabbitConstant.EXCHANGE_DIRECT_ORDER, "add", new HashMap<>());

        // 消费者订阅消息 监听如上声明的队列 (队列名, 是否自动应答(与消息可靠有关 后续会介绍), 消费者标签, 消费者)
        channel.basicConsume(declareOk.getQueue(), true, "RGP订单系统ADD处理逻辑消费者", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(consumerTag);
                System.out.println(envelope.toString());
                System.out.println(properties.toString());
                System.out.println("消息内容:" + new String(body));
            }
        });

        // 消费roberto.order.add.failure队列
        channel.basicConsume(declareOk2.getQueue(), false, "RGP订单系统ADD FAILURE处理逻辑消费者", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    System.out.println("----------roberto.order.add.failure----------");
                    System.out.println(new String(body, "UTF-8"));
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e) {
                    channel.basicNack(envelope.getDeliveryTag(), false, true);
                }
            }
        });
    }

}
