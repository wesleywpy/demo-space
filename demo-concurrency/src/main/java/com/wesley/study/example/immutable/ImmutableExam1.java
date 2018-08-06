package com.wesley.study.example.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * 使用不可变对象,保证对象发布的安全性
 * @author Wesley Created By 2018/8/4
 */
public class ImmutableExam1 {

    private static ImmutableList<Integer> immutableList = ImmutableList.of(1, 2, 3, 4);

    private static ImmutableSet immutableSet = ImmutableSet.copyOf(immutableList);


    public static void main(String[] args) {
        immutableList.add(1);
    }
}
