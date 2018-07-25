package com.wesley.study.spring.amqp.converter.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesley.study.config.RabbitConstant;
import com.wesley.study.spring.amqp.converter.consumer.Order;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生产者
 * @author Created by Wesley on 2018/7/24.
 */
@ComponentScan(basePackages = "com.wesley.study.spring.amqp.converter.producer")
public class ProducerApplication {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ProducerApplication.class);
        RabbitAdmin rabbitAdmin = applicationContext.getBean(RabbitAdmin.class);
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);

        // 声明Exchange
        rabbitAdmin.declareExchange(new DirectExchange(RabbitConstant.EXCHANGE_DIRECT_ORDER, true, false));

        // 发送字符串
        sendString(rabbitTemplate);
        // 发送单个对象JSON
        sendSingle(rabbitTemplate);
        // 发送List集合JSON
        sendList(rabbitTemplate);
        // 发送Map集合JSON
        sendMap(rabbitTemplate);
        // 发送图片
        sendImage(rabbitTemplate);
    }

    private static void sendImage(RabbitTemplate rabbitTemplate) throws IOException {
        File file = new File("C:\\Users\\Administrator\\Downloads\\bg-50.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        int length;
        byte[] b = new byte[1024];
        while ((length = fileInputStream.read(b)) != -1) {
            byteArrayOutputStream.write(b, 0, length);
        }
        fileInputStream.close();
        byteArrayOutputStream.close();
        byte[] buffer = byteArrayOutputStream.toByteArray();

        // 声明消息 (消息体, 消息属性)
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("_extName", "jpg");
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("image/jpg");
        Message message = new Message(buffer, messageProperties);

        rabbitTemplate.send(RabbitConstant.EXCHANGE_DIRECT_ORDER, "add", message);
    }

    private static void sendMap(RabbitTemplate rabbitTemplate) throws JsonProcessingException {
        Order order = new Order("OD0000001", BigDecimal.valueOf(888888.888888));
        Order order2 = new Order("OD0000002", BigDecimal.valueOf(888888.6666));
        Map<String, Order> orderMap = new HashMap<>();
        orderMap.put(order.getOrderId(), order);
        orderMap.put(order2.getOrderId(), order2);

        ObjectMapper objectMapper = new ObjectMapper();

        // 声明消息 (消息体, 消息属性)
        MessageProperties messageProperties = new MessageProperties();
        // 当发送Map集合对象的JSON数据时，需要在消息的header中将__TypeId__指定为java.util.Map，
        // 并且需要额外指定属性__KeyTypeId__用于告知客户端Map中key的类型，__ContentTypeId__用于告知客户端Map中Value的类型
        messageProperties.getHeaders().put("__TypeId__", "java.util.Map");
        messageProperties.getHeaders().put("__KeyTypeId__", "java.lang.String");
        messageProperties.getHeaders().put("__ContentTypeId__", "order");

        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("application/json");


        Message message = new Message(objectMapper.writeValueAsString(orderMap).getBytes(), messageProperties);

        rabbitTemplate.send(RabbitConstant.EXCHANGE_DIRECT_ORDER, "add", message);
    }

    private static void sendList(RabbitTemplate rabbitTemplate) throws JsonProcessingException {
        Order order = new Order("OD0000001", BigDecimal.valueOf(888888.888888));
        Order order2 = new Order("OD0000002", BigDecimal.valueOf(888888.6666));
        List<Order> orderList = Arrays.asList(order, order2);

        ObjectMapper objectMapper = new ObjectMapper();

        // 声明消息 (消息体, 消息属性)
        MessageProperties messageProperties = new MessageProperties();
        // 当发送List集合对象的JSON数据时，需要在消息的header中将__TypeId__指定为java.util.List，
        // 并且需要额外指定属性__ContentTypeId__用户告知消费者List集合中的对象类型
        messageProperties.getHeaders().put("__TypeId__", "java.util.List");
        messageProperties.getHeaders().put("__ContentTypeId__", "order");
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("application/json");
        Message message = new Message(objectMapper.writeValueAsString(orderList).getBytes(), messageProperties);

        rabbitTemplate.send(RabbitConstant.EXCHANGE_DIRECT_ORDER, "add", message);
    }

    private static void sendSingle(RabbitTemplate rabbitTemplate) {
        Order order = new Order("OD0000001", BigDecimal.valueOf(888888.6666));

        // 声明消息 (消息体, 消息属性)
        MessageProperties messageProperties = new MessageProperties();
        // 当发送普通对象的JSON数据时，需要在消息的header中增加一个__TypeId__的属性告知消费者是哪个对象
        messageProperties.getHeaders().put("__TypeId__", "order");
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Message message = new Message(objectMapper.writeValueAsBytes(order), messageProperties);
            rabbitTemplate.send(RabbitConstant.EXCHANGE_DIRECT_ORDER, "add", message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static void sendString(RabbitTemplate rabbitTemplate) {
        // 声明消息 (消息体, 消息属性)
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("text/plain");
        Message message = new Message("订单消息".getBytes(), messageProperties);
        rabbitTemplate.send(RabbitConstant.EXCHANGE_DIRECT_ORDER, "add", message);
    }
}
