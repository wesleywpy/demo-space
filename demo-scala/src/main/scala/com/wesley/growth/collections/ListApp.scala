package com.wesley.growth.collections

import scala.collection.mutable.ListBuffer

/**
  * <p>
  *
  * </p>
  * Email yani@uoko.com
  *
  * @author Created by Yani on 2018/10/15
  */
object ListApp extends App {

    // 空集合
    val nil = Nil
    println(nil)

    // :: 连接集合
    val list1 = 1 :: 2 :: Nil
    val list2 = 3 :: list1
    println(list2)


    // 长度可变
    val listBuf = ListBuffer[Int]()
    listBuf += (2,3,4)
    listBuf ++= List(5,6,7)

    println(listBuf.head)
    // 取除第一个元素外剩余的元素，返回的是列表
    println(listBuf.tail)

}
