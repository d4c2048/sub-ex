package com.lee.controller;

import cn.hutool.core.bean.BeanUtil;
import com.lee.pojo.Course;
import com.lee.service.ICourseService;
import com.lee.service.IStudentService;
import com.lee.utils.StudentHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

import static com.lee.utils.Constants.*;

/**
 * Student表控制层
 * @author Lee
 * @since 2022 -05-12 13:49:40
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    /**
     * 服务对象
     */
    @Resource
    private IStudentService studentService;
    @Resource
    private ICourseService courseService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取所有课程
     * @param token 登陆令牌
     * @return 结果 all courses
     */
    @GetMapping("/courses")
    public R getAllCourses(String token) {
        List<Course> res = courseService.getAllCourses(token);
        if (BeanUtil.isEmpty(res) || res.size() == 0) {
            return R.error(GLOBAL_ERR);
        }
        return R.ok(res);
    }

    /**
     * 获取学生已选的课程
     * @param token 登陆令牌
     * @return 结果 selected courses
     */
    @GetMapping("/select_courses")
    public R getSelectedCourses(String token) {
        List<Course> res = courseService.getSelectedCourses(StudentHolder.getStudent().getStuId(), token);
        if (BeanUtil.isEmpty(res) || res.size() == 0) {
            return R.error(GLOBAL_ERR);
        }
        return R.ok(res);
    }

    /**
     * 返回所有课程是否已选的布尔数组
     * @param token the token
     * @return 结果 boolean array
     */
    @GetMapping("/is_select")
    public R getBooleanArray(String token) {
        List<Course> courses = courseService.getAllCourses(token);
        List<Course> sCourses = courseService.getSelectedCourses(StudentHolder.getStudent().getStuId(), token);
        boolean[] res = new boolean[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            for (Course s : sCourses) {
                if (courses.get(i).getCosId().equals(s.getCosId())) {
                    res[i] = true;
                    break;
                }
                res[i] = false;
            }
        }
        return R.ok(res);
    }

    /**
     * 执行选课服务
     * @param token 登陆令牌
     * @param cosId 课程编号
     * @return 结果
     */
    @PostMapping("/select/{cosId}")
    public R select(@RequestParam String token, @PathVariable Integer cosId) {
        if (!canSelect(cosId)) {
            return R.error(SELECT_ERR_MESSAGE);
        }
        courseService.selectCourse(cosId, token);
        return R.ok();
    }

    private synchronized boolean canSelect(Integer cosId) {
        if (Integer.parseInt(String.valueOf(stringRedisTemplate.opsForHash().get(COURSE_SUM_REDIS_KEY, cosId.toString()))) > 0) {
            stringRedisTemplate.opsForHash().increment(COURSE_SUM_REDIS_KEY, cosId.toString(), 1);
            return true;
        }
        return false;
    }

    /**
     * 执行取消选课服务
     * @param cosId 课程编号
     * @param token 登陆令牌
     * @return 结果
     */
    @PostMapping("/cancel/{cosId}")
    public R cancel(@PathVariable Integer cosId, @RequestParam String token) {
        stringRedisTemplate.opsForHash().increment(COURSE_SUM_REDIS_KEY, cosId.toString(), -1);
        courseService.cancelCourse(cosId, token);
        return R.ok();
    }

    /**
     * 跳转页面到/student/s_courses
     * @return 页面
     */
    @GetMapping("/s_courses")
    public ModelAndView sCourses() {
        return new ModelAndView("/student/s_courses");
    }

    /**
     * 跳转页面到/student/index
     * @return 页面
     */
    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("/student/index");
    }

}

