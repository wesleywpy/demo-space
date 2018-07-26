package com.wesley.study.attribute.alternate.exchange.amqp;

import com.wesley.study.config.RabbitConstant;
import com.wesley.study.spring.amqp.auto.declare.consumer.MessageHandler;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wesley Created By 2018/7/24
 */
@Configuration
public class SpringAMQPConsumerConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        com.rabbitmq.client.ConnectionFactory connectionFactory = new com.rabbitmq.client.ConnectionFactory();

        // 配置连接信息
        connectionFactory.setHost(RabbitConstant.HOST);
        connectionFactory.setPort(RabbitConstant.PORT);
        connectionFactory.setVirtualHost(RabbitConstant.VIRTUAL_HOST);
        connectionFactory.setUsername(RabbitConstant.USERNAME);
        connectionFactory.setPassword(RabbitConstant.PASSWORD);

        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(10000);

        Map<String, Object> connectionFactoryPropertiesMap = new HashMap<>();
        connectionFactoryPropertiesMap.put("principal", "RobertoHuang");
        connectionFactoryPropertiesMap.put("description", "RGP订单系统V2.0");
        connectionFactoryPropertiesMap.put("emailAddress", "RobertoHuang@foxmail.com");
        connectionFactory.setClientProperties(connectionFactoryPropertiesMap);

        return new CachingConnectionFactory(connectionFactory);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public List<Exchange> listExchange() {
        FanoutExchange fanoutExchange = new FanoutExchange("wesley.order.failure", true, false, new HashMap<>());
        // 声明AE 类型为Fanout
        Map<String, Object> exchangeProperties = new HashMap<>();
        exchangeProperties.put("alternate-exchange", "wesley.order.failure");
        DirectExchange directExchange = new DirectExchange(RabbitConstant.EXCHANGE_DIRECT_ORDER, true, false, exchangeProperties);
        return Arrays.asList(fanoutExchange, directExchange);
    }

    @Bean
    public List<Queue> listQueue() {
        Queue queue1 = new Queue(RabbitConstant.QUEUE_ORDER_ADD, true, false, false, new HashMap<>());
        Queue queue2 = new Queue("wesley.order.add.failure", true, false, false, new HashMap<>());
        return Arrays.asList(queue1, queue2);
    }

    @Bean
    public List<Binding> listBinding() {
        Binding binding1 = BindingBuilder.bind(new Queue(RabbitConstant.QUEUE_ORDER_ADD)).to(new DirectExchange(RabbitConstant.EXCHANGE_DIRECT_ORDER)).with("add");
        Binding binding2 = BindingBuilder.bind(new Queue("wesley.order.failure.add")).to(new DirectExchange("wesley.order.failure")).with("");
        return Arrays.asList(binding1, binding2);
    }

    /**
     * MessageListenerContainer类中定义了消息消费的逻辑
     */
    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        messageListenerContainer.setQueueNames(RabbitConstant.QUEUE_ORDER_ADD);

        // 设置消费者线程数
        messageListenerContainer.setConcurrentConsumers(5);
        // 设置最大消费者线程数
        messageListenerContainer.setMaxConcurrentConsumers(10);

        // 设置消费者属性信息
        Map<String, Object> argumentMap = new HashMap<>();
        messageListenerContainer.setConsumerArguments(argumentMap);

        // 设置消费者标签
        messageListenerContainer.setConsumerTagStrategy(s -> "RGP订单系统ADD处理逻辑消费者");

        messageListenerContainer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println("----------roberto.order.add----------");
                    System.out.println(new String(message.getBody(), "UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return messageListenerContainer;
    }
    @Bean
    public MessageListenerContainer messageListenerContainer2(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        messageListenerContainer.setQueueNames(RabbitConstant.QUEUE_ORDER_ADD);

        // 设置消费者线程数
        messageListenerContainer.setConcurrentConsumers(5);
        // 设置最大消费者线程数
        messageListenerContainer.setMaxConcurrentConsumers(10);

        // 设置消费者属性信息
        Map<String, Object> argumentMap = new HashMap<>();
        messageListenerContainer.setConsumerArguments(argumentMap);

        // 设置消费者标签
        messageListenerContainer.setConsumerTagStrategy(s -> "RGP订单系统ADD FAILURE 处理逻辑消费者");

        messageListenerContainer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println("----------roberto.order.add.failure----------");
                    System.out.println(new String(message.getBody(), "UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return messageListenerContainer;
    }

}
