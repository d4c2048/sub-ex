package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.pojo.Student;
import com.lee.dao.StudentDao;
import com.lee.service.IStudentService;
import com.lee.utils.StudentHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Student表服务实现类
 *
 * @author Lee
 * @since 2022 -05-12 13:49:45
 */
@Service("studentService")
@Transactional(rollbackFor = Exception.class)
public class StudentServiceImpl implements IStudentService {

    @Resource
    private StudentDao studentDao;

    @Override
    public Student findById(Integer stuId) {
        return studentDao.selectById(stuId);
    }

    @Override
    public Student login(Student student) {
        Student student1 = studentDao.selectOne(new QueryWrapper<>(student));
        System.out.println("保存了student");
        StudentHolder.saveStudent(student1);
        return student1;
    }
}
