package com.wesley.growth.ml

import breeze.linalg.DenseMatrix
import org.apache.spark.mllib.linalg.{Matrices, Matrix}
import org.apache.spark.mllib.stat.Statistics


/**
  * <p>
  *
  * </p>
  * Email yani@uoko.com
  *
  * @author Created by Yani on 2018/10/25
  */
object MatrixApp extends App{

    val dm:Matrix = Matrices.dense(3, 2, Array(1.0, 3.0, 5.0, 2.0, 4.0, 6.0))
    println(dm)

    val d1 = DenseMatrix(Array(1,2), Array(3,4), Array(5,6))
    println(d1)

    println(d1.t)
    println(d1.reshape(2, 3))

    // 皮尔森卡方检验
    val pValue = Statistics.chiSqTest(Matrices.dense(2,2, Array(127, 19, 147 ,10)))
    print(pValue)
}
