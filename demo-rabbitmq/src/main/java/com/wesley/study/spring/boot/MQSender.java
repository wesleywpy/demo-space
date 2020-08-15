package com.wesley.study.spring.boot;

import com.wesley.study.config.MQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Created by Wesley on 2018/6/25.
 */
@Service
public class MQSender {

    private static Logger log = LoggerFactory.getLogger(MQSender.class);
    @Autowired
    AmqpTemplate amqpTemplate ;


    public void send(Object message) {
		log.info("send message: {}", message);
		amqpTemplate.convertAndSend(MQConfig.QUEUE_DIRECT, message);
	}

	public void sendTopic(Object message) {
		log.info("send topic message: {}", message);
		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", message+ "1");
		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", message+ "2");
	}
}
