package com.wesley.growth.streaming;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author yani
 * Email yani@uoko.com
 * Created by 2018/09/19
 */
public class KafkaConsumer {

    public static void main(String[] args) throws Exception{
        if (args.length != 2) {
            System.err.println("Usage: KafkaConsumer <brokers> <topics>");
            System.exit(1);
        }

        SparkConf sparkConf = new SparkConf()
                .setAppName("KafkaConsumer")
                // 本地调试模式
                .setMaster("local[2]");

        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(5));
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", args[0]);
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "test_group");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);

        List<String> topics = Arrays.asList(StringUtils.split(args[1], ","));
        JavaInputDStream<ConsumerRecord<String, String>> stream = KafkaUtils.createDirectStream(
                ssc, LocationStrategies.PreferConsistent(), ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams));

        stream.mapToPair(record -> {
            System.out.println(record.key() + " -> " + record.value());
            return new Tuple2<>(record.key(), record.value());
        })
              .print();

        ssc.start();
        ssc.awaitTermination();
    }
}
