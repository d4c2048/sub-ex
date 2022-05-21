package com.lee.service;

import com.lee.pojo.Student;

/**
 * Student表服务接口
 *
 * @author Lee
 * @since 2022 -05-12 13:49:44
 */
public interface IStudentService {

    /**
     * 根据学生Id查找学生
     *
     * @param stuId 学生Id
     * @return 学生对象 student
     */
    Student findById(Integer stuId);

    /**
     * 学生登陆业务
     *
     * @param student 包含学号和密码的学生对象
     * @return 学生对象 student
     */
    Student login(Student student);

}
