package com.wesley.growth;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author yani
 * Created by 2018/09/06
 */
public class KafkaConsumerTopology {

    public static class PrintBlot extends BaseBasicBolt {
        @Override
        public void execute(Tuple input, BasicOutputCollector collector) {
            System.out.println("kafka message -> ");
            input.getValues().forEach(System.out::println);
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {

        }
    }



    public static void main(String[] args) {
        if (Objects.isNull(args) || args.length < 2){
            System.out.println("请指定Kakfa节点和Topic \n 格式: node-1:9092,node-9093 topic");
            return;
        }

        KafkaSpoutConfig<String, String> kafkaSpoutConfig = KafkaSpoutConfig.builder(args[0], args[1])
//                                                                            .set
                                                                            .build();
        final TopologyBuilder tp = new TopologyBuilder();

        // 2个 executor 线程
        tp.setSpout("kafka_spout", new KafkaSpout<>(kafkaSpoutConfig), 2);

        tp.setBolt("PrintBlot", new PrintBlot(), 2).localOrShuffleGrouping("kafka_spout");

        String topoName = KafkaConsumerTopology.class.getSimpleName();
        Config config = new Config();
        try {
            StormSubmitter.submitTopology(topoName, config, tp.createTopology());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        LocalCluster cluster = new LocalCluster();
//        cluster.submitTopology(topoName, config, tp.createTopology());
    }
}
