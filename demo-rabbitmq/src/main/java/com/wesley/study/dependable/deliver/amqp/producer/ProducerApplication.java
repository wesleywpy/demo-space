package com.wesley.study.dependable.deliver.amqp.producer;

import com.wesley.study.config.RabbitConstant;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 创建生产者启动类 发送两条消息(修改其中一条消息的Routing key让消息不能被正确路由)，同时在发送消息时设置CorrelationData属性
 *
 * @author Created by Wesley on 2018/7/24.
 */
@ComponentScan(basePackages = "com.wesley.study.dependable.deliver.amqp.producer")
public class ProducerApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ProducerApplication.class);
        RabbitAdmin rabbitAdmin = applicationContext.getBean(RabbitAdmin.class);
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);

        // 声明Exchange
        rabbitAdmin.declareExchange(new DirectExchange(RabbitConstant.EXCHANGE_DIRECT_ORDER, true, false));

        // 声明消息 (消息体, 消息属性)
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("UTF-8");
        Message message = new Message("订单信息".getBytes(), messageProperties);

        // 发布消息 (交换机名, Routing key, 消息);
        rabbitTemplate.send(RabbitConstant.EXCHANGE_DIRECT_ORDER, RabbitConstant.ROUTING_KEY_ADD, message, new CorrelationData("201810725116"));
        rabbitTemplate.send(RabbitConstant.EXCHANGE_DIRECT_ORDER, "addXXX", message, new CorrelationData("201810725116"));
    }
}
