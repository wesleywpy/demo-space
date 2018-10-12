package com.wesley.growth.traits

/**
  * <p>
  *
  * </p>
  *
  * @author Created by Yani on 2018/10/11
  * Email yani@uoko.com
  */
trait Dao {

    // 抽象字段
    var id:Long

    // 常量
    val dialect:String = "mysql"

    // 普通方法
    def delete(id:String):Boolean = {
        println("delete -> "+ id)
        true
    }

    // 定义一个抽象方法
    def add(o:Any):Boolean

    def update(o:Any):Int

    def query(id:String):List[Any]
}
