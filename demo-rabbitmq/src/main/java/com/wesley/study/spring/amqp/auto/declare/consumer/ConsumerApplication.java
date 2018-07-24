package com.wesley.study.spring.amqp.auto.declare.consumer;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 自动声明 交换机、队列和绑定
 * @author Wesley Created By 2018/7/24
 */
@ComponentScan(basePackages = "com.wesley.study.spring.amqp.auto.declare.consumer")
public class ConsumerApplication {

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(ConsumerApplication.class);
    }
}
