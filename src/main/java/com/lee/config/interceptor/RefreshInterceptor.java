package com.lee.config.interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.lee.pojo.Student;
import com.lee.utils.StudentHolder;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.lee.utils.Constants.STUDENT_REDIS_PREFIX;
import static com.lee.utils.Constants.STUDENT_REDIS_TTL;
import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * The type Refresh interceptor.
 *
 * @author Lee
 */
@AllArgsConstructor
public class RefreshInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        String token = request.getParameter("token");
        if(StrUtil.isBlank(token)) {
            return true;
        }
        String key = STUDENT_REDIS_PREFIX + token;
        Map<Object, Object> studentMap = stringRedisTemplate.opsForHash().entries(key);
        if(studentMap.isEmpty()) {
            return true;
        }
        Student student = BeanUtil.fillBeanWithMap(studentMap, new Student(), false);
        StudentHolder.saveStudent(student);
        stringRedisTemplate.expire(key, STUDENT_REDIS_TTL, MINUTES);
        return true;
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) {
        StudentHolder.removeStudent();
    }

}