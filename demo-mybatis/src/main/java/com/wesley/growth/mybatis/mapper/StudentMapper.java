package com.wesley.growth.mybatis.mapper;

import com.wesley.growth.mybatis.entity.StudentEntity;
import org.apache.ibatis.annotations.Param;

public interface StudentMapper {

	public StudentEntity getStudentById(int id);

	public int addStudent(StudentEntity student);

	public int updateStudentName(@Param("name") String name, @Param("id") int id);

	public StudentEntity getStudentByIdWithClassInfo(int id);
}
