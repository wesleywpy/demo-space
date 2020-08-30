package com.wesley.growth.mp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author Created by Wesley on 2020/08/30
 */
@SpringBootApplication
@MapperScan("com.wesley.growth.mp.mapper")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
