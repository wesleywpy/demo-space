package com.wesley.growth.matching

/**
  * <p>
  * 模式匹配Demo
  * </p>
  * Email yani@uoko.com
 *
  * @author Created by Yani on 2018/10/15
  */
object PatternMatching extends App {

    val scores = Array(100, 60, 80, 93, 77, 44)

    for(i <- scores){
        i match {
            case 100 => println("满分")
            case 80 => println("良")
            case 60 => println("合格")
            case _ if i < 80 && i > 60 => println("良")
            case _ => println("D")
        }
    }

    // 匹配序列, 比如数组,List,Set
    def matchingCivilizations(civilizations:Array[String]):Unit = {
        civilizations match {
            case Array("China") => println("中华文明")
            // 匹配只有两个元素的情况
            case Array(x, y) => println(x + "和" + y + "文明")
            case Array("China", _*) => println("中华文明和其它文明")
            case _ => println(civilizations.mkString(","))
        }
    }

    matchingCivilizations(Array("China"))
    matchingCivilizations(Array("Inca", "Mapuche"))
    matchingCivilizations(Array("China", "Japan", "Korea"))
    matchingCivilizations(Array("Roman", "Greece"))

    // 数据类型匹配
    def matchingType(t:Any): Unit = {
        t match {
            case i:Int => println("Integer")
            case s:String => println("String")
            case m:Map[_,_] => println("Map")
            case _ => println("Other Type")
        }
    }

    matchingType(555)
    matchingType(Map("key" -> "val"))
    matchingType(Nil)
    matchingType("haha")


    // 偏函数
    def translate:PartialFunction[Int,String] = {
        case 60 => "合格"
        case 100 => "满分"
    }


}