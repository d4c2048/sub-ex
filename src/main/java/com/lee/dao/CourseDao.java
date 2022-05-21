package com.lee.dao;

import com.lee.pojo.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Course表数据库访问层
 *
 * @author Lee
 * @since 2022 -05-13 20:09:16
 */
@Mapper
public interface CourseDao extends BaseMapper<Course> {

    /**
     * 获取所有课程信息
     *
     * @return 所有课程 list
     */
    List<Course> selectAllCourses();

    /**
     * Select course by id course.
     *
     * @param cosId the cos id
     * @return the course
     */
    Course selectCourseById(int cosId);

    /**
     * 获取该学生所选的所有课程信息
     *
     * @param stuId 学生学号
     * @return 所选的所有课程 list
     */
    List<Course> selectSelectedCourses(int stuId);

    /**
     * 减少已选课的人数
     *
     * @param cosId 课程编号
     */
    @Update("update tb_course set cos_now=cos_now-1 where cos_id=#{cosId}")
    void decreaseCosNow(int cosId);

    /**
     * 增加已选课的人数
     *
     * @param cosId 课程编号
     */
    @Update("update tb_course set cos_now=cos_now+1 where cos_id=#{cosId}")
    void increaseCosNow(int cosId);

}

