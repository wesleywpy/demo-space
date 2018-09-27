package com.wesley.growth.sql;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;

/**
 * <p>
 * DataFrame API基本操作
 * </p>
 *
 * @author yani
 * Email yani@uoko.com
 * Created by 2018/09/27
 */
public class DataFrameApp {

    public static void main(String[] args) throws IOException {
        SparkSession sparkSession = SparkSession.builder()
                .appName("DataFrameApp")
                .master("local[2]")
                .getOrCreate();

        String jsonFile = DataFrameApp.class.getClassLoader().getResource("people.json").toString();
        Dataset<Row> dataset = sparkSession.read().json(jsonFile);

        // 输出dataframe对应的schema信息
        dataset.printSchema();

        dataset.show();

        // 查询某列所有的数据： select name from table
        dataset.select("name").show();

        // 根据某一列的值进行过滤： select * from table where age > 21
        dataset.filter(dataset.col("age").gt(21)).show();

        sparkSession.stop();
    }
}
