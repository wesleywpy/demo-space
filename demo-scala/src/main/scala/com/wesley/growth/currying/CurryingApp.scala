package com.wesley.growth.currying

/**
  * <p>
  * 柯里化
  * </p>
  * Email yani@uoko.com
  *
  * @author Created by Yani on 2018/10/15
  */
object CurryingApp extends App{

    def multiplyBy(factor:Double)(x:Double) = x * factor

    println(multiplyBy(50)(2))


    // 柯里化函数应用
    val m = multiplyBy(5)_

    println(m(5))
}
