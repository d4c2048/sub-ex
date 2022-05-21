package com.lee.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.lee.dao.CourseDao;
import com.lee.pojo.Course;
import com.lee.pojo.Section;
import com.lee.producer.MqProducer;
import com.lee.service.ICourseService;
import com.lee.utils.StudentHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lee.utils.Constants.*;
import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * Course表服务实现类
 *
 * @author Lee
 * @since 2022 -05-13 20:09:16
 */
@Service("courseService")
@Transactional(rollbackFor = Exception.class)
public class CourseServiceImpl implements ICourseService {

    @Resource
    private CourseDao courseDao;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private MqProducer producer;

    @Override
    public List<Course> getAllCourses(String token) {
        Map<Object, Object> courses = stringRedisTemplate.opsForHash().entries(COURSES_REDIS_KEY);
        if (MapUtil.isEmpty(courses)) {
            List<Course> courseList = courseDao.selectAllCourses();
            Map<String, String> courseMap = courseList
                    .stream()
                    .collect(Collectors.toMap(course -> course.getCosId().toString(), JSONUtil::toJsonStr));
            Map<String, String> sum = courseList
                    .stream()
                    .collect(Collectors.toMap(course -> course.getCosId().toString(),
                            course -> String.valueOf(course.getCosAll() - course.getCosNow())));
            stringRedisTemplate.opsForHash().putAll(COURSE_SUM_REDIS_KEY, sum);
            stringRedisTemplate.opsForHash().putAll(COURSES_REDIS_KEY, courseMap);
            stringRedisTemplate.expire(COURSES_REDIS_KEY, COURSE_SUM_REDIS_TTL, MINUTES);
            stringRedisTemplate.expire(COURSES_REDIS_KEY, COURSES_REDIS_TTL, MINUTES);
            return courseList
                    .stream()
                    .sorted(Comparator.comparingInt(Course::getCosId))
                    .collect(Collectors.toList());
        }
        stringRedisTemplate.expire(COURSES_REDIS_KEY, COURSE_SUM_REDIS_TTL, MINUTES);
        stringRedisTemplate.expire(COURSES_REDIS_KEY, COURSES_REDIS_TTL, MINUTES);
        return courses
                .values()
                .stream()
                .map(course -> JSONUtil.toBean((String) course, Course.class))
                .sorted(Comparator.comparingInt(Course::getCosId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> getSelectedCourses(Integer stuId, String token) {
        String key = SELECTED_REDIS_PREFIX + token;
        Map<Object, Object> courses = stringRedisTemplate.opsForHash().entries(key);
        if (MapUtil.isEmpty(courses)) {
            List<Course> courseList = courseDao.selectSelectedCourses(stuId);
            Map<String, String> courseMap = courseList
                    .stream()
                    .collect(Collectors.toMap(course -> course.getCosId().toString(), JSONUtil::toJsonStr));
            stringRedisTemplate.opsForHash().putAll(key, courseMap);
            stringRedisTemplate.expire(key, SELECTED_REDIS_TTL, MINUTES);
            return courseList;
        }
        stringRedisTemplate.expire(key, SELECTED_REDIS_TTL, MINUTES);
        return courses
                .values()
                .stream()
                .map(course -> JSONUtil.toBean((String) course, Course.class))
                .collect(Collectors.toList());
    }

    @Override
    public void selectCourse(Integer cosId, String token) {
        Section section = new Section(null, StudentHolder.getStudent().getStuId(), cosId);
        producer.sendSelectMessage(JSONUtil.toJsonStr(section));
        String key = SELECTED_REDIS_PREFIX + token;
        Map<String, String> map = stringRedisTemplate.opsForHash().entries(key)
                .values().stream()
                .map(course -> JSONUtil.toBean((String) course, Course.class))
                .collect(Collectors.toMap(course -> course.getCosId().toString(), JSONUtil::toJsonStr));
        map.put(cosId.toString(), JSONUtil.toJsonStr(courseDao.selectCourseById(cosId)));
        stringRedisTemplate.opsForHash().putAll(key, map);
        stringRedisTemplate.expire(key, SELECTED_REDIS_TTL, MINUTES);
    }

    @Override
    public void cancelCourse(Integer cosId, String token) {
        Section section = new Section(null, StudentHolder.getStudent().getStuId(), cosId);
        producer.sendCancelMessage(JSONUtil.toJsonStr(section));
        String key = SELECTED_REDIS_PREFIX + token;
        Map<String, String> map = stringRedisTemplate.opsForHash().entries(key)
                .values().stream()
                .map(course -> JSONUtil.toBean((String) course, Course.class))
                .collect(Collectors.toMap(course -> course.getCosId().toString(), JSONUtil::toJsonStr));
        map.remove(cosId.toString());
        stringRedisTemplate.opsForHash().delete(key, stringRedisTemplate.opsForHash().keys(key).toArray());
        stringRedisTemplate.opsForHash().putAll(key, map);
        stringRedisTemplate.expire(key, SELECTED_REDIS_TTL, MINUTES);
    }

    @Override
    public void incrementCosNow(Integer cosId) {
        courseDao.increaseCosNow(cosId);
    }

    @Override
    public void decrementCosNow(Integer cosId) {
        courseDao.decreaseCosNow(cosId);
    }

}
