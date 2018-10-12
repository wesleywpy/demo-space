package com.wesley.growth

/**
  * <p>
  *
  * </p>
  *
  * @author Created by Wesley on 2018/09/21
  */
object Test {

    def main(args: Array[String]): Unit = {
//        val increase = (x:Int) => x + 1
//        println(increase(10))
//
//        val user = User("wes", 10, 22)
//        println(user)

        val s1 = "123"
        val s2 = new String("123")

        // 不区分 原生类型和应用类型
        println(s1 == s2)

        // 判断两个应用类型是否相等
        println(s1.eq(s2))

    }
}
