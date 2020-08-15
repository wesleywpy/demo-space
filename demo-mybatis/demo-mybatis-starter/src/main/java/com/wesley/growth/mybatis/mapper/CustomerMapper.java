package com.wesley.growth.mybatis.mapper;

import com.wesley.growth.mybatis.entity.Customer;

import java.util.List;

public interface CustomerMapper {
    int insert(Customer record);

    List<Customer> selectAll();
}