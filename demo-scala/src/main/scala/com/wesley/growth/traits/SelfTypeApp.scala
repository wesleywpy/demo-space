package com.wesley.growth.traits

/**
  *
  *
  *
  * Email yani@uoko.com
  * @author Created by Yani on 2018/10/12
  */
object SelfTypeApp {

    def main(args: Array[String]): Unit = {
        var maugham = new Fictionist("Maugham")

        maugham.writing()
    }
}

trait Write {
    def writing()
}

class Author(name:String) {

    // 定义this的别名
    auth:Write =>

    override def writing(): Unit = println(name + "is writing ...")

}

// 因为父类定义 "auth:Write =>", 子类Fictionist 必须实现 Write接口
class Fictionist(name:String) extends Author(name) with Write {

    override def writing(): Unit = println(name + "is writing a book.")

}
