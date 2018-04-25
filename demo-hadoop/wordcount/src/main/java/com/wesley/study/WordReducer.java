package com.wesley.study;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

/**
 * @author Created by Wesley on 2017/6/19.
 */
public class WordReducer extends Reducer<Text, IntWritable, Text, LongWritable>{

    /**
     *
     * @param key
     * @param values
     * @param context
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        long count = 0;
        for (IntWritable val : values)
            count += val.get();

        context.write(key, new LongWritable(count));
    }
}
