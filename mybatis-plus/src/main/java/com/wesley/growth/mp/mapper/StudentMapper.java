package com.wesley.growth.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wesley.growth.mp.domain.dto.SearchDTO;
import com.wesley.growth.mp.entity.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentMapper extends BaseMapper<Student> {

    IPage<Student> findByPage(IPage<Student> page, @Param("search") SearchDTO dto);

    /**
     * 自定义SQL方法
     * @return
     */
    int deleteAll();

}
