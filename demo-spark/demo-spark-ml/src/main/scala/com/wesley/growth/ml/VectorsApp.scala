package com.wesley.growth.ml

import breeze.linalg.DenseVector
import org.apache.spark.mllib.linalg.{Vector, Vectors}


/**
  * <p>
  *
  * </p>
  * Email yani@uoko.com
  *
  * @author Created by Yani on 2018/10/25
  */
object VectorsApp {

    def main(args: Array[String]): Unit = {
        val dv:Vector = Vectors.dense(1.0, 0.0, 3.0)
        val denseVector1 = DenseVector(1, 2, 3)
        val denseVector2 = DenseVector(1, 2, 3)

        val sub = denseVector1 + denseVector2
        println(sub)

        // 向量乘法
        println(denseVector1 * denseVector2.t)

        println()
    }


}
