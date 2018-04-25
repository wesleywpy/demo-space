package com.wesley.study.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import java.util.Objects;

/**
 * 自定义Hive Function
 * @author Created by Wesley on 2017/12/11.
 */
public final class Lower extends UDF{

    /**
     * evalute方法不能返回void
     */
    public Text evaluate(final Text text){
        if(Objects.isNull(text)){
            return null;
        }
        return new Text(text.toString().toLowerCase());
    }
}
