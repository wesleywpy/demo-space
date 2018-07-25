package com.wesley.study.spring.amqp.annotation.concumer;

import com.wesley.study.config.RabbitConstant;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;

/**
 * @author Created by Wesley on 2018/7/25.
 */
@Component
@RabbitListener(
        bindings = {@QueueBinding(value = @Queue(value = RabbitConstant.QUEUE_ORDER_ADD, durable = "true", exclusive = "false"), exchange = @Exchange(RabbitConstant.EXCHANGE_DIRECT_ORDER))}
)
public class SpringAMQPMessageHandle {

    @RabbitHandler
    public void add(byte[] message) {
        try {
            System.out.println(new String(message, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
