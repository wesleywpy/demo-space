package com.wesley.growth.kafka.admin;

import org.apache.kafka.clients.admin.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

/**
 * AdminSample
 *
 * @author WangPanYong
 * @since 2020/11/06 17:07
 */
public class AdminSample {

    public static final String TOPIC_NAME = "wesley_topic";


    public static AdminClient adminClient() {
        Properties properties = new Properties();
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "10.0.17.88:9092");
        return AdminClient.create(properties);
    }

    /**
     * 创建 Topic
     */
    public static void createTopic() {
        AdminClient adminClient = adminClient();
        // 副本因子
        short rs = 1;
        NewTopic newTopic = new NewTopic(TOPIC_NAME, 1 , rs);
        CreateTopicsResult topics = adminClient.createTopics(Collections.singletonList(newTopic));
        System.out.println("CreateTopicsResult : "+ topics);
    }

    /**
     * 获取Topic列表
     * @throws Exception
     */
    public static void topicLists() throws Exception {
        AdminClient adminClient = adminClient();
        // 是否查看internal选项
        ListTopicsOptions options = new ListTopicsOptions();
        options.listInternal(true);
//        ListTopicsResult listTopicsResult = adminClient.listTopics();
        ListTopicsResult listTopicsResult = adminClient.listTopics(options);
        Set<String> names = listTopicsResult.names().get();
        Collection<TopicListing> topicListings = listTopicsResult.listings().get();
//        KafkaFuture<Map<String, TopicListing>> mapKafkaFuture = listTopicsResult.namesToListings();
        // 打印names
        names.forEach(System.out::println);
        // 打印topicListings
        topicListings.forEach(System.out::println);
    }

    public static void main(String[] args) throws Exception {
//        createTopic();
        topicLists();
    }

}
