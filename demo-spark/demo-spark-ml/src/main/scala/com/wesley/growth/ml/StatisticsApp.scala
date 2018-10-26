package com.wesley.growth.ml

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.{SparkConf, SparkContext}


/**
  * <p>
  * 基础统计
  * </p>
  * Email yani@uoko.com
  *
  * @author Created by Yani on 2018/10/25
  */
object StatisticsApp {

    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setMaster("local[2]").setAppName("StatisticsApp")
        val sc = new SparkContext(conf)
        val classpath = this.getClass.getResource("/").getPath

        val txt = sc.textFile(classpath + "beijing.txt")
        val data = txt.flatMap(_.split(",")).map(value => Vectors.dense(value.toDouble))
        val summary = Statistics.colStats(data)
        // 每一列的平均值
        println(summary.mean)
        // 方差
        println(summary.variance)
        // 每一列非0的数量
        println(summary.numNonzeros)
        // 每一列的最大值
        println(summary.max)


        val yearAnddata = sc.textFile(classpath + "beijing2.txt").flatMap(_.split(",")).map(_.toDouble)
        // 数据大于1000表示年
        val years = yearAnddata.filter(_ > 1000)
        // 降雨量
        val rainfall = yearAnddata.filter(_ < 1000)
        // 年份与降雨量相关系数
        println("年份与降雨量 皮尔逊相关系数: " + Statistics.corr(years, rainfall))

    }

}
