package com.wesley.growth.collections

import scala.collection.mutable.ArrayBuffer

/**
  * @author Created by Wesley on 2018/10/13
  */
object ArrayApp extends App {

    val num = Array(1, 2, 4, 5, 6, 9, 8)
    // 求和
    println(num.sum)
    // 排序 然后转换为 字符串
    println(num.sorted.mkString(","))

    // 可变数组
    val buf = ArrayBuffer[Int]()
    buf += 1
    buf ++= Array(2, 3, 5)

    println(buf)

    // 指定范围遍历
    for(ele <- 1 until buf.length){
        print(buf(ele))
    }

    println()

    // 指定位置插入元素
    buf.insert(3, 4)

    for(ele <- (2 until buf.length).reverse){
        print(buf(ele))
    }

}
