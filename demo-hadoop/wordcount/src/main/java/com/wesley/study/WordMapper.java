package com.wesley.study;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

/**
 * Mapper<输入键, 输入值，输出键，输出值>
 * @author Created by Wesley on 2017/6/19.
 */
public class WordMapper extends Mapper<LongWritable, Text, Text, IntWritable>{

    /**
     *
     * @param key 输入文件的KEY
     * @param value 输入文件的VALUE
     * @param context
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        final IntWritable one = new IntWritable(1);

        for(String word : value.toString().split("\t")){
            if(StringUtils.isNotBlank(word)){
                context.write(new Text(word), one);
            }
        }
    }
}
