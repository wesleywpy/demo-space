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

case class Author(name:String)

