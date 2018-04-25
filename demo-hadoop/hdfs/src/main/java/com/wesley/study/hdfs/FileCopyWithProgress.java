package com.wesley.study.hdfs;

import com.wesley.study.hdfs.support.FSUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * 将本地文件复制到HDFS
 * @author Created by Wesley on 2017/11/15.
 */
public class FileCopyWithProgress {

    /** 样例参数：C:\Users\Administrator\Desktop\flash.txt hdfs://192.168.2.190:9000/tmp/flash.txt
     * @param args args[0]:本地文件地址 args[1]:复制文件在hdfs上的地址
     */
    public static void main(String[] args) throws IOException {
        String localSrc = args[0];
        String dst = args[1];

        FileSystem fileSystem = FSUtils.getFileSystem();

        InputStream inputStream = new BufferedInputStream(new FileInputStream(localSrc));
        FSDataOutputStream outputStream = fileSystem.create(new Path(dst), () -> System.out.println("上传成功"));
        IOUtils.copyBytes(inputStream, outputStream, 4096 , true);

        IOUtils.closeStream(inputStream);
        IOUtils.closeStream(fileSystem);
    }

}
