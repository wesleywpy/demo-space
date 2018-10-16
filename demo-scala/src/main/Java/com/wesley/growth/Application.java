package com.wesley.growth;

/**
 * <p>
 *
 * </p>
 * Email yani@uoko.com
 *
 * @author Created by Yani on 2018/10/16
 */
public class Application {

    public static void main(String[] args) {
        // 直接调用Scala代码
        User user = new User("Wesley", 12);

        System.out.println(user.name());
        System.out.println(user);

        // 伴生对象方法当做静态方法使用
        User yani = User.apply("Yani", 22, 1351234);
        System.out.println("yani = " + yani);
    }
}
