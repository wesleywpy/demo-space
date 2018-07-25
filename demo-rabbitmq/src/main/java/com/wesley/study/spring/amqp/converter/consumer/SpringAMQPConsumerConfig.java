package com.wesley.study.spring.amqp.converter.consumer;

import com.wesley.study.config.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
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

    // 自动声明交换机
    // 如果要一次性声明多个 使用public List<Exchange> listExchange()即可
    @Bean
    public Exchange exchange() {
        return new DirectExchange(RabbitConstant.EXCHANGE_DIRECT_ORDER, true, false, new HashMap<>());
    }

    @Bean
    // 自动声明队列
    // 如果要一次性声明多个 使用public List<Queue> listQueue()即可
    public Queue queue() {
        return new Queue(RabbitConstant.QUEUE_ORDER_ADD, true, false, false, new HashMap<>());
    }

    @Bean
    // 自动声明绑定
    // 如果要一次性声明多个 使用public List<Binding> listBinding()即可
    public Binding binding() {
        return new Binding(RabbitConstant.QUEUE_ORDER_ADD, Binding.DestinationType.QUEUE, RabbitConstant.EXCHANGE_DIRECT_ORDER, RabbitConstant.ROUTING_KEY_ADD, new HashMap<>());
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
        messageListenerContainer.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String s) {
                return "RGP订单系统ADD处理逻辑消费者";
            }
        });

        // 使用setAutoStartup方法可以自动设置消息消费时机
        messageListenerContainer.setAutoStartup(true);

        /**
         *  Spring AMQP提供了消息处理器适配器的功能，它可以把一个纯POJO类适配成一个可以处理消息的处理器，
         *  默认处理消息的方法为handleMessage，可以通过setDefaultListenerMethod方法进行修改
         */
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new MessageHandle());
        // 设置默认处理消息方法
        messageListenerAdapter.setDefaultListenerMethod("handleMessage");
        Map<String, String> queueOrTagToMethodName = new HashMap<>();
        // 将roberto.order.add队列的消息 使用add方法进行处理
        queueOrTagToMethodName.put(RabbitConstant.QUEUE_ORDER_ADD, "add");
        messageListenerAdapter.setQueueOrTagToMethodName(queueOrTagToMethodName);

        messageListenerContainer.setMessageListener(messageListenerAdapter);

        // 设置消息转换器
        ContentTypeDelegatingMessageConverter converter = new ContentTypeDelegatingMessageConverter();
        StringMessageConverter stringMessageConverter = new StringMessageConverter();
        FileMessageConverter fileMessageConverter = new FileMessageConverter();
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();

        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("order", Order.class);
        DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
        javaTypeMapper.setIdClassMapping(idClassMapping);
        jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);

        // 设置text/html text/plain 使用StringMessageConverter
        converter.addDelegate("text/html", stringMessageConverter);
        converter.addDelegate("text/plain", stringMessageConverter);
        // 设置application/json 使用Jackson2JsonMessageConverter
        converter.addDelegate("application/json", jackson2JsonMessageConverter);
        // 设置image/jpg image/png 使用FileMessageConverter
        converter.addDelegate("image/jpg", fileMessageConverter);
        converter.addDelegate("image/png", fileMessageConverter);
        messageListenerAdapter.setMessageConverter(converter);

        return messageListenerContainer;
    }

}
