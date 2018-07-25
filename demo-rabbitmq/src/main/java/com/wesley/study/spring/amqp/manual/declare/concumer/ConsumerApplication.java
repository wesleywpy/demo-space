package com.wesley.study.spring.amqp.manual.declare.concumer;

import com.wesley.study.config.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import java.util.HashMap;

/**
 * 手动声明 Exchange,Queue,Binding
 * @author Created by Wesley on 2018/7/24.
 */
@ComponentScan(basePackages = "com.wesley.study.spring.amqp.manual.declare.concumer")
public class ConsumerApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConsumerApplication.class);
        RabbitAdmin rabbitAdmin = applicationContext.getBean(RabbitAdmin.class);
        MessageListenerContainer messageListenerContainer = applicationContext.getBean(MessageListenerContainer.class);

        // 声明队列 (队列名", 是否持久化, 是否排他, 是否自动删除, 队列属性);
        rabbitAdmin.declareQueue(new Queue(RabbitConstant.QUEUE_ORDER_ADD, true, false, false, new HashMap<>()));

        // 声明Direct Exchange (交换机名, 是否持久化, 是否自动删除, 交换机属性);
        DirectExchange directExchange = new DirectExchange(RabbitConstant.EXCHANGE_DIRECT_ORDER, true, false, new HashMap<>());
        rabbitAdmin.declareExchange(directExchange);

        // 将队列Binding到交换机上, Routing key为add
        Binding binding = BindingBuilder.bind(new Queue(RabbitConstant.QUEUE_ORDER_ADD)).to(new DirectExchange(RabbitConstant.EXCHANGE_DIRECT_ORDER)).with("add");
        rabbitAdmin.declareBinding(binding);

        messageListenerContainer.start();
    }

}
