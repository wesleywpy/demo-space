package com.wesley.growth.kafka.admin;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.config.ConfigResource;

import java.util.*;

/**
 * AdminSample
 *
 * @author WangPanYong
 * @since 2020/11/06 17:07
 */
public class AdminSample {

    public static final String TOPIC_NAME = "test";


    public static AdminClient adminClient() {
        Properties properties = new Properties();
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "10.0.17.99:9092");
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

    /**
     * 删除 Topic
     * @throws Exception
     */
    public static void delTopics() throws Exception {
        AdminClient adminClient = adminClient();
        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(Arrays.asList(TOPIC_NAME));
        deleteTopicsResult.all().get();
    }

    /**
        描述Topic
        name ：jiangzh-topic ,
        desc: (name=jiangzh-topic,
            internal=false,
            partitions=
                (partition=0,
                 leader=192.168.220.128:9092
                 (id: 0 rack: null),
                 replicas=192.168.220.128:9092
                 (id: 0 rack: null),
                 isr=192.168.220.128:9092
                 (id: 0 rack: null)),
                 authorizedOperations=[])
     */
    public static void describeTopics() throws Exception {
        AdminClient adminClient = adminClient();
        DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(Arrays.asList(TOPIC_NAME));
        Map<String, TopicDescription> stringTopicDescriptionMap = describeTopicsResult.all().get();
        Set<Map.Entry<String, TopicDescription>> entries = stringTopicDescriptionMap.entrySet();
        entries.forEach((entry)->{
            System.out.println("name ："+entry.getKey()+" , desc: "+ entry.getValue());
        });
    }

    public static void describeConfig() throws Exception{
        AdminClient adminClient = adminClient();
//        ConfigResource configResource = new ConfigResource(ConfigResource.Type.BROKER, TOPIC_NAME);

        ConfigResource configResource = new ConfigResource(ConfigResource.Type.TOPIC, TOPIC_NAME);
        DescribeConfigsResult describeConfigsResult = adminClient.describeConfigs(Arrays.asList(configResource));
        Map<ConfigResource, Config> configResourceConfigMap = describeConfigsResult.all().get();
        configResourceConfigMap.entrySet().forEach((entry)->{
            System.out.println("configResource : "+entry.getKey()+" , Config : "+entry.getValue());
        });
    }
    /*
           修改Config信息
        */
    public static void alterConfig() throws Exception{
        AdminClient adminClient = adminClient();
//        Map<ConfigResource,Config> configMaps = new HashMap<>();
//
//        // 组织两个参数
//        ConfigResource configResource = new ConfigResource(ConfigResource.Type.TOPIC, TOPIC_NAME);
//        Config config = new Config(Arrays.asList(new ConfigEntry("preallocate","true")));
//        configMaps.put(configResource,config);
//        AlterConfigsResult alterConfigsResult = adminClient.alterConfigs(configMaps);

        /*
            从 2.3以上的版本新修改的API
         */
        Map<ConfigResource,Collection<AlterConfigOp>> configMaps = new HashMap<>();
        // 组织两个参数
        ConfigResource configResource = new ConfigResource(ConfigResource.Type.TOPIC, TOPIC_NAME);
        AlterConfigOp alterConfigOp =
                new AlterConfigOp(new ConfigEntry("preallocate","false"),AlterConfigOp.OpType.SET);
        configMaps.put(configResource,Arrays.asList(alterConfigOp));

        AlterConfigsResult alterConfigsResult = adminClient.incrementalAlterConfigs(configMaps);
        alterConfigsResult.all().get();
    }

    /**
    * 增加partition数量
    */
    public static void incrPartitions(int partitions) throws Exception{
        AdminClient adminClient = adminClient();
        Map<String, NewPartitions> partitionsMap = new HashMap<>();
        NewPartitions newPartitions = NewPartitions.increaseTo(partitions);
        partitionsMap.put(TOPIC_NAME, newPartitions);
        CreatePartitionsResult createPartitionsResult = adminClient.createPartitions(partitionsMap);
        createPartitionsResult.all().get();
    }

    public static void main(String[] args) throws Exception {
//        createTopic();
//        topicLists();
//        describeTopics();
//        describeConfig();

        // 增加partition数量
        incrPartitions(2);
    }

}
