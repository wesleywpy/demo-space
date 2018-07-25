package com.wesley.study.dependable.deliver.client.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.ReturnListener;
import com.wesley.study.client.utils.ChannelUtils;
import com.wesley.study.config.RabbitConstant;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Wesley Created By 2018/7/25
 */
public class MessageProducer {

    public static void main(String[] args) throws IOException {
        Channel channel = ChannelUtils.getChannelInstance("RGP订单系统消息生产者");

        channel.exchangeDeclare(RabbitConstant.EXCHANGE_DIRECT_ORDER, BuiltinExchangeType.DIRECT, true, false, false, new HashMap<>());

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().deliveryMode(2).contentType("UTF-8").build();

        // 当消息没有被正确路由时 回调ReturnListener
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("replyCode:" + replyCode);
                System.out.println("replyText:" + replyText);
                System.out.println("exchange:" + exchange);
                System.out.println("routingKey:" + routingKey);
                System.out.println("properties:" + properties);
                System.out.println("body:" + new String(body, "UTF-8"));
            }
        });

        // 开启消息确认
        channel.confirmSelect();

        channel.addConfirmListener(new ConfirmListener() {

            /**
             * 如果消息没有被正确路由走的是ACK方法
             * @param deliveryTag
             * @param multiple
             */
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("----------Ack----------");
                System.out.println(deliveryTag);
                System.out.println(multiple);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("----------Nack----------");
                System.out.println(deliveryTag);
                System.out.println(multiple);
            }
        });

        // 将mandatory属性设置成true
        channel.basicPublish(RabbitConstant.EXCHANGE_DIRECT_ORDER, "add", true, basicProperties, "订单信息".getBytes());

        channel.basicPublish(RabbitConstant.EXCHANGE_DIRECT_ORDER, "addXXX", true, basicProperties, "订单信息".getBytes());
    }
}
