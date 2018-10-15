package com.wesley.growth.matching

/**
  * <p>
  *
  * </p>
  * Email yani@uoko.com
  *
  * @author Created by Yani on 2018/10/15
  */
object CaseClassMatching {

    def main(args: Array[String]): Unit = {

        val stu:Person = Student("xiaoming", 9527)

        stu match {
            case Student(name, stuNo) => println("Student, name:"+ name)
            case Teacher(name, stuNo) => println("Teacher, name:"+ name)
        }
    }
}

// sealed修饰的class 进行模式匹配的时候，确保所有的可能情况都被列出, 否则编译器会警告
sealed abstract class Person

case class Student(name:String, stuNo: Int) extends Person

case class Teacher(name:String, teaNo: Int) extends Person
