package com.wesley.study.spring.boot.producer;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;

/**
 * @author Wesley Created By 2018/7/26
 */
@Configuration
public class RabbitMQProducerConfig {

    @Bean
    public Exchange exchange() {
        return new DirectExchange("wesley.order", true, false, new HashMap<>());
    }
}
