package com.wesley.growth

/**
  *
  * 当主构造器的参数不用var或val修饰的时候，参数会生成类的私有val成员，并且不会产生getter和setter方法
  * var 修饰时: 产生getter/setter
  * val 修饰时: 变量为final类型, 只有getter
  */
class User (var name:String, val age:Int){

    var phone:Int = 0

    // 重写 toString 方法
    override def toString: String = name + ","+ age + "," + phone

    /**
      * 辅助构造函数
      * 定义辅助构造函数时，必须先调用主构造函数或其它已经定义好的构造函数。
      */
    def this(name:String, age:Int, phone:Int){
        this(name,age)
        this.phone = phone
    }

}

// 伴生对象
object User {

    def apply(name: String, age: Int) = new User(name,age)
    def apply(name: String, age: Int, phone:Int) = new User(name,age, phone)
}