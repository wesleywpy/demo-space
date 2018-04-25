package com.wesley.study.hdfs;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Created by Wesley on 2017/11/15.
 */
public class URLCat {

    static {
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
    }

    /**
     * 从HDFS中读取文件
     * @param args 样例：hdfs://192.168.2.190:9000/sample.txt
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        try(InputStream in = new URL(args[0]).openStream()){
            IOUtils.copyBytes(in, System.out, 4096 , true);
        }
    }
}
