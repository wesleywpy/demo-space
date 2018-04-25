package com.wesley.study.udf;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import java.util.Objects;

/**
 * 去除文本中的双引号
 * @author Created by Wesley on 2017/12/11.
 */
public final class RemoveQuoterUDF extends UDF{

    /**
     * evalute方法不能返回void
     */
    public Text evaluate(final Text text){
        Text empty = new Text("");

        if(Objects.isNull(text)){
            return empty;
        }

        String targetTxt = text.toString();
        if(StringUtils.isBlank(targetTxt)){
            return empty;
        }

        return new Text(StringUtils.replace(targetTxt, "\"", ""));
    }
}
