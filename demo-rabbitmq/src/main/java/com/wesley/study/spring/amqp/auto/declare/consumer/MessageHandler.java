package com.wesley.study.spring.amqp.auto.declare.consumer;

import java.io.UnsupportedEncodingException;

/**
 * @author Wesley Created By 2018/7/24
 */
public class MessageHandler {

    public void add(byte[] message) {
        try {
            System.out.println(new String(message, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
