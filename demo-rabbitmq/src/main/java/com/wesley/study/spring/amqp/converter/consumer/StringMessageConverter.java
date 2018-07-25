package com.wesley.study.spring.amqp.converter.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import java.io.UnsupportedEncodingException;

/**
 * @author Created by Wesley on 2018/7/25.
 */
public class StringMessageConverter implements MessageConverter {

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        return null;
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        try {
            return new String(message.getBody(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new MessageConversionException("StringMessageConverter转换失败", e);
        }
    }
}
