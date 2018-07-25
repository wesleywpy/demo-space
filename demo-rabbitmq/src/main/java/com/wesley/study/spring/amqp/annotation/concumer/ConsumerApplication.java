package com.wesley.study.spring.amqp.annotation.concumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 使用RabbitListener注解进行消息消费
 * @author Created by Wesley on 2018/7/24.
 */
@EnableRabbit
@ComponentScan(basePackages = "com.wesley.study.spring.amqp.annotation.concumer")
public class ConsumerApplication {

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(ConsumerApplication.class);
    }

}
