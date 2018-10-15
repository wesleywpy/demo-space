package com.wesley.growth.collections

/**
  * <p>
  *
  * </p>
  * Email yani@uoko.com
  *
  * @author Created by Yani on 2018/10/15
  */
object TupleApp extends App {

    val host = ("localhost", 8080)

    for(e <- 0 until host.productArity){
        println(host.productElement(e))
    }

}
