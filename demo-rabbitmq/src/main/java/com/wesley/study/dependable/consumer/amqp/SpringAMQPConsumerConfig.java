package com.wesley.study.dependable.consumer.amqp;

import com.rabbitmq.client.Channel;
import com.wesley.study.config.RabbitConstant;
import com.wesley.study.spring.amqp.auto.declare.consumer.MessageHandler;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConsumerTagStrategy;
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

        // 设置消息确认模式为手动模式
        messageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        messageListenerContainer.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                try {
                    System.out.println(new String(message.getBody(), "UTF-8"));
                    System.out.println(message.getMessageProperties());
                    if ("订单信息2".equals(new String(message.getBody(), "UTF-8"))) {
                        throw new RuntimeException();
                    } else {
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    }
                } catch (Exception e) {
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                }
            }
        });

        return messageListenerContainer;
    }

}
