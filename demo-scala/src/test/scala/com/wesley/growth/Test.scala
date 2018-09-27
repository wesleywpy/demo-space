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
        val increase = (x:Int) => x + 1
        println(increase(10))

        val user = User("wes", 10, 22)
        println(user)
    }
}
