package com.wesley.study.rpc.server;

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
public class RPCServer {
    public static void main(String[] args) throws IOException {
        Channel channel = ChannelUtils.getChannelInstance("RGP订单系统Server端");

        channel.queueDeclare(RabbitConstant.QUEUE_ORDER_ADD, true, false, false, new HashMap<>());
        channel.exchangeDeclare(RabbitConstant.EXCHANGE_DIRECT_ORDER, BuiltinExchangeType.DIRECT, true, false, false, new HashMap<>());
        channel.queueBind(RabbitConstant.QUEUE_ORDER_ADD, RabbitConstant.EXCHANGE_DIRECT_ORDER, "add", new HashMap<>());

        // 服务端监听一个队列，监听客户端发送过来的消息
        channel.basicConsume(RabbitConstant.QUEUE_ORDER_ADD, true, "RGP订单系统Server端", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String replyTo = properties.getReplyTo();
                String correlationId = properties.getCorrelationId();

                System.out.println("----------收到RPC调用请求消息----------");
                System.out.println(consumerTag);
                System.out.println("消息属性为:" + properties);
                System.out.println("消息内容为" + new String(body));
                try {
                    String orderId = RPCMethod.addOrder(new String(body));
                    AMQP.BasicProperties replyProperties = new AMQP.BasicProperties().builder().deliveryMode(2).contentType("UTF-8").correlationId(correlationId).build();
                    // 从消息属性中获取reply_to，correlation_id属性，把调用结果发送给reply_to指定的队列，发送的消息属性要带上correlation_id
                    channel.basicPublish("", replyTo, replyProperties, orderId.getBytes());
                    System.out.println("----------RPC调用成功 结果已返回----------");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
