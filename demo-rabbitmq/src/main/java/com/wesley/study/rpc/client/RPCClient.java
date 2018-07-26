package com.wesley.study.rpc.client;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.wesley.study.client.utils.ChannelUtils;
import com.wesley.study.config.RabbitConstant;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author Created by Wesley on 2018/7/26.
 */
public class RPCClient {

    public static void main(String[] args) throws IOException {
        Channel channel = ChannelUtils.getChannelInstance("RGP订单系统Client端");

//        channel.exchangeDeclare(RabbitConstant.EXCHANGE_DIRECT_ORDER, BuiltinExchangeType.DIRECT, true, false, new HashMap<>());
        String replyTo = "welsey.order.add.replay";
        channel.queueDeclare(replyTo, true, false, false, new HashMap<>());
        String correlationId = UUID.randomUUID().toString();
        // 发送消息，消息属性需要带上reply_to,correlation_id
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().deliveryMode(2)
                .contentType("UTF-8")
                .correlationId(correlationId)
                .replyTo(replyTo).build();
        channel.basicPublish("welsey.order", "add", false, basicProperties, "订单消息信息".getBytes());

        channel.basicConsume("welsey.order.add.replay", true, "RGP订单系统Client端", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //服务端处理完之后reply_to对应的队列就会收到异步处理结果消息
                System.out.println("----------RPC调用结果----------");
                System.out.println(consumerTag);
                System.out.println("消息属性为:" + properties);
                System.out.println("消息内容为" + new String(body));
            }
        });
    }
}
