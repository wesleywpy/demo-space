package com.wesley.growth.mp.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wesley.growth.mp.entity.Student;
import com.wesley.growth.mp.mapper.StudentMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author Created by Wesley on 2020/09/07
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
