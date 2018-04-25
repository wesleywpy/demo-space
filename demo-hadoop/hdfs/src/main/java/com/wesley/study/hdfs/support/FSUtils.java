package com.wesley.study.hdfs.support;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import java.io.IOException;

/**
 * @author Created by Wesley on 2017/11/24.
 */
@Slf4j
public abstract class FSUtils {

    private FSUtils(){

    }

    public static FileSystem getFileSystem(){
        Configuration configuration = new Configuration();
        try {
            return FileSystem.get(configuration);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
