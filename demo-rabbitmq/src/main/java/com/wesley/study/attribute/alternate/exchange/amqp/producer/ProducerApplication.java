package com.wesley.study.attribute.alternate.exchange.amqp.producer;

import com.wesley.study.config.RabbitConstant;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import java.util.HashMap;
import java.util.Map;

/**
 * 生产者
 * @author Created by Wesley on 2018/7/24.
 */
@ComponentScan(basePackages = "com.wesley.study.attribute.alternate.exchange.amqp.producer")
public class ProducerApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ProducerApplication.class);
        RabbitAdmin rabbitAdmin = applicationContext.getBean(RabbitAdmin.class);
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);

        // 声明AE 类型为Fanout
        rabbitAdmin.declareExchange(new FanoutExchange("wesley.order.failure", true, false, new HashMap<>()));

        // wesley.order设置AE
        Map<String, Object> exchangeProperties = new HashMap<>();
        exchangeProperties.put("alternate-exchange", "wesley.order.failure");
        rabbitAdmin.declareExchange(new DirectExchange(RabbitConstant.EXCHANGE_DIRECT_ORDER, true, false, exchangeProperties));

        // 声明消息 (消息体, 消息属性)
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("UTF-8");
        Message message = new Message("订单信息".getBytes(), messageProperties);

        rabbitTemplate.send(RabbitConstant.EXCHANGE_DIRECT_ORDER, "addXXX", message);

    }
}
