package com.wesley.growth

/**
  * <p>
  * 高阶函数demo
  * </p>
  * Email yani@uoko.com
  * @author Created by Yani on 2018/10/15
  */
object FuncationApp extends App{

    val name = "Wesley"

    println(s"$name is learning")

    // 多行字符串
//    val strs =
//    """
//      |MUTILINE
//      |A
//      |B
//      |C
//    """.stripMargin
//    println(strs)

    val nums = List(1,3,5,7,9)

    // 参数只有一个元素可以省略括号
    //    nums.map((x) => x*2)
    nums.map(x => x*2).foreach(println)

    // 参数在右边只出现一次的话，还可以用占位符的表示方式
    nums.map(_ * 2).foreach(println)

    // 最后两个元素
//    println(nums.reduceRight(_ - _))
    println(nums.reduceRight((x:Int, y:Int) => {
        print(x,y)
        x - y
    }))

//    nums.fold(1)((x:Int, y:Int) => {
//        print(x,y)
//        x + y
//    })

    // 压平
    val list = List(List(1,3,5),List(2,4,6))
    println(list.flatMap(_.map(_*2)))


}
