package com.wesley.study.rpc.server;

/**
 * 模拟RPC调用方法
 * @author Created by Wesley on 2018/7/26.
 */
public class RPCMethod {
    public static String addOrder(String orderInfo) {
        try {
            System.out.println("orderInfo已添加到数据库");
            return "订单ID";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
