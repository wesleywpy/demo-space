package com.wesley.growth.source

import scala.io.Source

/**
  * <p>
  *
  * </p>
  * Email yani@uoko.com
  *
  * @author Created by Yani on 2018/10/16
  */
object FileApp {

    def main(args: Array[String]): Unit = {
        val flash = Source.fromFile("C:\\Users\\yani\\Desktop\\flash.txt", "UTF-8")

        flash.getLines().foreach(println)

        flash.close()

    }

}
