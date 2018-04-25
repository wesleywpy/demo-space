package com.wesley.study.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import java.io.IOException;

/**
 * MapReduce编写模板
 * @author Created by Wesley on 2017/11/28.
 */
public class ModuleMapReduce extends Configured implements Tool {


    /**
     * 使用步骤:
     * 1. todo 修改Map与Reduce 输入输出数据类型
     * 2. todo Shuffle参数配置(可选)
     */
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        //compress
        configuration.set("mapreduce.map.output.compress", "true");
        configuration.set("mapreduce.map.output.compress.codec", "org.apache.hadoop.io.compress.SnappyCodec");
        int result = ToolRunner.run(configuration, new ModuleMapReduce(), args);
        System.exit(result);
    }


    @Override
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(getConf(), getClass().getSimpleName());

        //输入数据的路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));

        //map
        job.setMapperClass(ModuleMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        /**
         * Shuffle参数配置
         */
//        job.setPartitionerClass();
//        job.setSortComparatorClass();
//        job.setCombinerClass();
//        job.setGroupingComparatorClass();

        //reduce
        job.setReducerClass(ModuleReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        //输出数据的路径(只能有一个)
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        return job.waitForCompletion(true) ? 0 : 1;
    }


    /**
     * Map模板
     */
    public static class ModuleMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            super.map(key, value, context);
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            super.cleanup(context);
        }
    }


    /**
     * Reduce模板
     */
    public static class ModuleReducer extends Reducer<Text, IntWritable, Text, LongWritable> {
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
        }

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            super.reduce(key, values, context);
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            super.cleanup(context);
        }
    }

}
