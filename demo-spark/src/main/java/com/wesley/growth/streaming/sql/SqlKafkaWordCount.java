package com.wesley.growth.streaming.sql;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>
 * SparkStreaming整合SparkSql
 * 通过kafka统计单词
 * </p>
 *
 * @author yani
 * Email yani@uoko.com
 * Created by 2018/09/27
 */
public class SqlKafkaWordCount {
    private static final Pattern SPACE = Pattern.compile(" ");

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            System.err.println("Usage: SqlKafkaWordCount <brokers> <topics>");
            System.exit(1);
        }

        SparkConf sparkConf = new SparkConf()
                .setAppName("SqlKafkaWordCount")
                .set("spark.eventLog.enabled", "false")
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
        JavaInputDStream<ConsumerRecord<String, String>> lines = KafkaUtils.createDirectStream(
                ssc, LocationStrategies.PreferConsistent(), ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams));

        // 将kafka的一条信息按 空格 分隔
        JavaDStream<String> words = lines.flatMap(record -> Arrays.asList(SPACE.split(record.value())).iterator());
        words.foreachRDD((rdd, time) -> {
            SparkSession sparkSession = SparkSessionSingleton.getInstance(rdd.context().getConf());

            // 创建DataFrame
            JavaRDD<Word> wordsRdd = rdd.map((Function<String, Word>) Word::new);
            Dataset<Row> wordsDFrame = sparkSession.createDataFrame(wordsRdd, Word.class);
            wordsDFrame.createOrReplaceTempView("words");

            Dataset<Row> wordCountsDataFrame = sparkSession.sql("select word, count(*) as total from words group by word");
            System.out.println("========= " + time + "=========");
            wordCountsDataFrame.show();
        });

        ssc.start();
        ssc.awaitTermination();
    }
}
