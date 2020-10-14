package com.wesley.growth.clazz;

/**
 * StaticResolution
 * 方法静态解析演示
 * @author WangPanYong
 * @since 2020/09/29 11:36
 */
public class StaticResolution {
    public static void sayHello() {
        System.out.println("hello world");
    }

    public static void main(String[] args) {
        StaticResolution.sayHello();
    }
}
