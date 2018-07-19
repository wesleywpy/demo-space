package com.wesley.study.client.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by Wesley on 2018/7/19.
 */
public class ChannelUtils {
    public static Channel getChannelInstance(String connectionDescription){
        ConnectionFactory connectionFactory = getConnectionFactory();
        try {
            return connectionFactory.newConnection(connectionDescription).createChannel();
        } catch (Exception e) {
            throw new RuntimeException("获取Channel连接失败");
        }
    }

    private static ConnectionFactory getConnectionFactory(){
        ConnectionFactory connectionFactory = new ConnectionFactory();

        // 配置连接信息
        connectionFactory.setHost("192.168.2.38");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("wesley");
        connectionFactory.setPassword("123456");

        // 网络异常自动连接恢复
        connectionFactory.setAutomaticRecoveryEnabled(true);
        // 每10秒尝试重试连接一次
        connectionFactory.setNetworkRecoveryInterval(10000);

        // 设置ConnectionFactory属性信息
        Map<String, Object> connectionFactoryPropertiesMap = new HashMap<>();
        connectionFactoryPropertiesMap.put("principal", "RobertoHuang");
        connectionFactoryPropertiesMap.put("description", "RGP订单系统V2.0");
        connectionFactoryPropertiesMap.put("emailAddress", "RobertoHuang@foxmail.com");
        connectionFactory.setClientProperties(connectionFactoryPropertiesMap);

        return connectionFactory;
    }
}
