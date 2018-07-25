package com.wesley.study.spring.amqp.converter.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.util.FileCopyUtils;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件消息转换器
 * @author Created by Wesley on 2018/7/25.
 */
public class FileMessageConverter implements MessageConverter{

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        return null;
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        String extName = (String) message.getMessageProperties().getHeaders().get("_extName");
        byte[] bytes = message.getBody();
        String fileName = UUID.randomUUID().toString();
        String filePath = System.getProperty("java.io.tmpdir") + fileName + "." + extName;
        File tempFile = new File(filePath);
        try {
            FileCopyUtils.copy(bytes, tempFile);
        } catch (IOException e) {
            throw new MessageConversionException("FileMessageConverter消息转换失败", e);
        }
        return tempFile;
    }
}
