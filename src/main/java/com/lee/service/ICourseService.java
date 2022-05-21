package com.lee.service;

import com.lee.pojo.Course;

import java.util.List;

/**
 * Course表服务接口
 *
 * @author Lee
 * @since 2022 -05-13 20:09:16
 */
public interface ICourseService {

    /**
     * 获取所有课程信息
     *
     * @param token 用户令牌
     * @return 所有课程 all courses
     */
    List<Course> getAllCourses(String token);

    /**
     * 获取所有该学生已选的课程信息
     * @param stuId 该学生的Id
     * @param token 用户令牌
     * @return 所有该学生已选的课程 selected courses
     */
    List<Course> getSelectedCourses(Integer stuId, String token);

    /**
     * 学生选择了课程
     *
     * @param cosId 课程编号
     * @param token 登陆令牌
     */
    void selectCourse(Integer cosId, String token);

    /**
     * 学生取消了选课
     *
     * @param cosId 课程编号
     * @param token 登陆令牌
     */
    void cancelCourse(Integer cosId, String token);

    /**
     * 当前选课人数减一
     *
     * @param cosId 课程编号
     */
    void decrementCosNow(Integer cosId);

    /**
     * 当前选课人数加一
     *
     * @param cosId 课程编号
     */
    void incrementCosNow(Integer cosId);

}
