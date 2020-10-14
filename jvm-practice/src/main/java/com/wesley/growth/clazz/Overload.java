package com.wesley.growth.clazz;

/**
 * Overload
 *  重载方法匹配优先级
 * @author WangPanYong
 * @since 2020/10/13 10:44
 */
public class Overload {
//    public static void sayHello(Object arg) {
//        System.out.println("hello Object");
//    }

//    public static void sayHello(int arg) {
//        System.out.println("hello int");
//    }

//    public static void sayHello(long arg) {
//        System.out.println("hello long");
//    }

//    public static void sayHello(Character arg) {
//        System.out.println("hello Character");
//    }

//    public static void sayHello(char arg) {
//        System.out.println("hello char");
//    }

    /**
     * 可见变长参数的重载优先级是最低的
     */
    public static void sayHello(char... arg) {
        System.out.println("hello char ...");
    }

//    public static void sayHello(Serializable arg) {
//        System.out.println("hello Serializable");
//    }

    public static void main(String[] args) {
        // 实际上自动转型还能继续发生多次，按照char>int>long>float>double的顺序转型进行匹配
        sayHello('a');
    }
}
