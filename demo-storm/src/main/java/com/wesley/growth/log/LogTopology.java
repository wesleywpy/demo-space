package com.wesley.growth.log;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.hbase.bolt.HBaseBolt;
import org.apache.storm.hbase.bolt.mapper.SimpleHBaseMapper;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author yani
 * Email yani@uoko.com
 * Created by 2018/09/11
 */
public class LogTopology {
    private static final String KAFKA_SERVERS = "hadoop01:9092";
    private static final String KAFKA_TOPIC_LOG = "logs-logstash";

    private static final String SPOUT_KAFKA = "kafka_spout";
    private static final String BOLT_LOG = "log_bolt";
    private static final String BOLT_HBASE = "hbase_bolt";


    public static void main(String[] args) {
        if(Objects.isNull(args) || args.length < 2){
            System.out.println("Topology Name: " + args[0] + ", Hbase Table Name: " + args[1]);
            System.exit(0);
        }

        // 设置kafka偏移量
        KafkaSpoutConfig<String, String> kafkaSpoutConfig = KafkaSpoutConfig.builder(KAFKA_SERVERS, KAFKA_TOPIC_LOG)
                                                                            .setFirstPollOffsetStrategy(KafkaSpoutConfig.FirstPollOffsetStrategy.UNCOMMITTED_LATEST)
                                                                            .build();

        Map<String, Object> hbConf = new HashMap<>();
        hbConf.put("hbase.rootdir", "hdfs://hadoop01:9000/hbase");
        hbConf.put("hbase.zookeeper.property.clientPort", "2181");
        hbConf.put("hbase.zookeeper.quorum", "hadoop01,hadoop02,hadoop03");

        Config config = new Config();
        config.put("hbase.conf", hbConf);

        SimpleHBaseMapper mapper = new SimpleHBaseMapper()
                .withRowKeyField(LogBolt.FILED_ROWKEY)
                .withColumnFields(new Fields(LogBolt.FILED_LONGITUDE, LogBolt.FILED_LATITUDE))
                .withColumnFamily("cf1");

        HBaseBolt hbase = new HBaseBolt(args[1], mapper).withConfigKey("hbase.conf");
        final TopologyBuilder tp = new TopologyBuilder();

        // 2个 executor 线程
        tp.setSpout(SPOUT_KAFKA, new KafkaSpout<>(kafkaSpoutConfig), 2);
        tp.setBolt(BOLT_LOG, new LogBolt(), 2).localOrShuffleGrouping(SPOUT_KAFKA);

        Fields fields = new Fields(LogBolt.FILED_ROWKEY, LogBolt.FILED_LONGITUDE, LogBolt.FILED_LATITUDE);
        tp.setBolt(BOLT_HBASE, hbase, 1).fieldsGrouping(BOLT_LOG, fields);

        try {
            StormSubmitter.submitTopology(args[0], config, tp.createTopology());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        LocalCluster cluster = new LocalCluster();
//        cluster.submitTopology("LogTopology", config, tp.createTopology());
    }
}
