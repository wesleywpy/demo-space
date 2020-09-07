package com.wesley.growth.mp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;

@TableName("student")
public class Student implements Serializable {

	// 学号
	@TableId(type = IdType.INPUT)
	private Integer id;

	// 姓名
	private String name;

	// 年龄
	private Integer age;

	// 班级
	@TableField(exist = false)
	private String className;

	/**
	 * 乐观锁
	 */
	@Version
	private Integer version;
	/**
	 * 自动填充字段
	 */
	@TableField(fill = FieldFill.INSERT)
	private Integer createBy;
	@TableField(fill = FieldFill.UPDATE)
	private Integer updateBy;

	/**
	 * 状态[0:未删除,1:删除]：逻辑删除
	 */
	@TableLogic
	@TableField(select = false)
	private Integer isDeleted;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Student{");
		sb.append("id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", age=").append(age);
		sb.append('}');
		return sb.toString();
	}
}
