package com.wesley.study.udf;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * 转换时间格式
 * @author Created by Wesley on 2017/12/11.
 */
public final class DateConverterUDF extends UDF{
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);
    private String targetFormat = "yyyy-MM-dd HH:mm:ss";
    /**
     * evalute方法不能返回void
     */
    public Text evaluate(final Text text){
        if(Objects.isNull(text)){
            return null;
        }

        String targetTxt = text.toString();
        if(StringUtils.isBlank(targetTxt)){
            return null;
        }

        try {
            Date date = dateFormat.parse(targetTxt);
            return new Text(DateFormatUtils.format(date, targetFormat));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
