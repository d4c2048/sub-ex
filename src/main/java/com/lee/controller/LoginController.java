package com.lee.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.lee.pojo.Student;
import com.lee.service.IStudentService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.lee.utils.Constants.LOGIN_ERR_MESSAGE;
import static com.lee.utils.Constants.STUDENT_REDIS_PREFIX;

/**
 * The type Login controller.
 *
 * @author HASEE
 */
@RestController
public class LoginController {

    @Resource
    private IStudentService studentService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 执行登录业务
     * @param student 学生对象
     * @return 结果
     */
    @PostMapping("/login/do")
    public R login(@RequestBody Student student) {
        System.out.println("stuId = " + student.getStuId());
        System.out.println("pwd = " + student.getPwd());
        Student studentRes = studentService.login(student);
        if (BeanUtil.isEmpty(studentRes)) {
            return R.error(LOGIN_ERR_MESSAGE);
        }
        String token = UUID.randomUUID().toString(true);
        Map<String, Object> mapObj = BeanUtil.beanToMap(studentRes, false, true);
        System.out.println(mapObj);
        Map<String, String> map = new HashMap<>(mapObj.size(), 1);
        for (Map.Entry<String, Object> entry : mapObj.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toString());
        }
        stringRedisTemplate.opsForHash().putAll(STUDENT_REDIS_PREFIX + token, map);
        return R.ok(token);
    }

    /**
     * 跳转页面到login
     * @return 页面
     */
    @GetMapping("/login")
    public ModelAndView toIndex() {
        return new ModelAndView("login");
    }

}
