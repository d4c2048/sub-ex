package com.lee.dao;

import com.lee.pojo.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Student表数据库访问层
 *
 * @author Lee
 * @since 2022 -05-12 13:49:43
 */
@Mapper
public interface StudentDao extends BaseMapper<Student> {

}

