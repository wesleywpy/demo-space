package com.wesley.study.example.immutable;

/**
 * 设计一个不可变类, 类用final修饰
 * @author Created by Wesley on 2019/7/28
 */
public final class ImmutableExam2 {

    /**
     * 成员变量必须私有，并且加上final修饰
     */
    private final int[] arrayField;


    public ImmutableExam2(int[] arr) {
        // 赋值使用深度Copy, 防止在外部修改arr的值
        this.arrayField = arr.clone();
    }

    /**
     * 不提供Setter方法, 并且在Getter方法返回, 对象的拷贝
     * @return
     */
    public int[] getArrayField() {
        return arrayField.clone();
    }


}
