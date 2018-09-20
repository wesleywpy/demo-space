package com.wesley.growth.streaming.log;

import com.google.common.collect.Lists;
import com.wesley.growth.streaming.log.dao.ClickCountDao;
import com.wesley.growth.streaming.log.domain.ClickCount;
import com.wesley.growth.streaming.log.domain.ClickLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
 * Created by 2018/09/20
 */
public class LogAnalysisApp {

    private static final String KAFKA_BOOTSTRAP_SERVERS = "hadoop01:9092,hadoop02:9092,hadoop03:9092";
    private static final String KAFKA_LOGS_TOPIC = "access-log";

    public static void main(String[] args) throws Exception {
        SparkConf sparkConf = new SparkConf()
                .setAppName("LogAnalysisApp")
                .setMaster("local[2]");

        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(10));
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", KAFKA_BOOTSTRAP_SERVERS);
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "logs_group");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);

        List<String> topics = Lists.newArrayList(KAFKA_LOGS_TOPIC);
        JavaInputDStream<ConsumerRecord<String, String>> stream = KafkaUtils.createDirectStream(
                ssc, LocationStrategies.PreferConsistent(), ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams));

        // 日志格式: 101.10.63.17	2018-09-20 11:28:22	"GET /room/wenjiangqu HTTP/1.1"	200	https://search.yahoo.com/search?p=优客逸家 成都 建设路房源
        JavaDStream<ClickLog> logsStream = stream.map(record -> {
            String value = record.value();
            String[] infos = StringUtils.split(value, "\t");

            // "GET /room/wenjiangqu HTTP/1.1"
            String area = StringUtils.split(infos[2], " ")[1];

            LocalDateTime dateTime = LocalDateTime.parse(infos[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String time = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

            return new ClickLog(infos[0], time, StringUtils.substringAfter(area, "/room/"), Integer.parseInt(infos[3]), infos[4]);
        }).filter(clickLog -> StringUtils.isNotBlank(clickLog.getArea()));

        // 统计访问量

        logsStream.mapToPair(clickLog -> new Tuple2<>(StringUtils.substring(clickLog.getTime(), 0, 8) + "_" + clickLog.getArea(), 1))
                  .reduceByKey((i1, i2) -> i1 + i2)
                  .foreachRDD(rdd -> {

                      List<ClickCount> clickCounts = rdd.map(tuple2 -> new ClickCount(tuple2._1, tuple2._2)).collect();
                      System.out.println(clickCounts);
                      ClickCountDao.save(clickCounts);
                  });

        logsStream.print();

        ssc.start();
        ssc.awaitTermination();
    }
}
