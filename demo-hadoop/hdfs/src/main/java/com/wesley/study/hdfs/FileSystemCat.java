package com.wesley.study.hdfs;

import com.wesley.study.hdfs.support.FSUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Created by Wesley on 2017/11/15.
 */
public class FileSystemCat {
    /**
     * 读取文件
     */
    public static void main(String[] args) throws IOException{
        String uri = args[0];

        //封装了客户端或服务器的配置
        FileSystem fileSystem = FSUtils.getFileSystem();

        try(InputStream in = fileSystem.open(new Path(uri))){
            IOUtils.copyBytes(in, System.out, 4096 , true);
        }finally {
            fileSystem.close();
        }
    }
}
