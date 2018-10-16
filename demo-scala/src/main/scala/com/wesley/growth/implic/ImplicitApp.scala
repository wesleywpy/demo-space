package com.wesley.growth.implic

import com.wesley.growth.implic.ImplicitAspect._
/**
  * <p>
  *
  * </p>
  * Email yani@uoko.com
  *
  * @author Created by Yani on 2018/10/16
  */
object ImplicitApp {

    // 隐式参数
    implicit val act: String = "laughing"

    def main(args: Array[String]): Unit = {
        val p = new Person()
        p.destoryWall()

        perform()
    }

    // 通过柯里化函数定义, 配合implicit修饰变量, 在调用函数时可以省略对应参数
    def perform()(implicit act:String): Unit = println(s"perform $act")

}

class Person(){
    def fearWall(): Unit = println("敬畏城墙")
}

class Giant(){
    def destoryWall(): Unit = println("摧毁城墙")
}

