package com.wesley.growth.class_object

/**
  * <p>
  *
  * </p>
  * Email yani@uoko.com
  *
  * @author Created by Yani on 2018/10/12
  */
object CaseClassApp {

    def main(args: Array[String]): Unit = {
        var maugham = Author("maugham")
        print(maugham)
    }

}

// 一旦某个class被定义case clas编译器会自动生成该类的伴生对象，伴生对象中包括了apply方法及unapply方法，
// apply方法使得我们可以不需要new关键字就可以创建对象，
// unapply方法，则使得可以方便地应用在模式匹配当中，另外编译器还自动地帮我们实现对应的toString、equals、copy等方法
case class Author(name:String)

// 不会生成apply、unapply方法
case object Writer{

}