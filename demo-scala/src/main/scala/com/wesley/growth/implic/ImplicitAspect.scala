package com.wesley.growth.implic

/**
  * <p>
  * 封装隐式转换函数
  * </p>
  * Email yani@uoko.com
  *
  * @author Created by Yani on 2018/10/16
  */
object ImplicitAspect {

    implicit def awaken(p:Person): Giant = new Giant()


}
