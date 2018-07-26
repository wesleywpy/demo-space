package com.wesley.study.spring.boot;

import com.wesley.study.config.MQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author Created by Wesley on 2018/6/25.
 */
@Service
public class MQReceiver {
    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @RabbitListener(queues=MQConfig.QUEUE_DIRECT)
    public void receive(String message) {
        log.info("receive message: {}", message);
    }

    @RabbitListener(queues= MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info(" topic  queue1 message: {}", message);
    }

    @RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info(" topic  queue2 message: {}", message);
    }
}
