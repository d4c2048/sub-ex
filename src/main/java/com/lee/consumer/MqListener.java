package com.lee.consumer;

import cn.hutool.json.JSONUtil;
import com.lee.pojo.Course;
import com.lee.pojo.Section;
import com.lee.service.ICourseService;
import com.lee.service.ISectionService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lee.utils.Constants.*;
import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * Mq监听器
 *
 * @author Lee
 */
@Component
public class MqListener {

    @Resource
    private ISectionService sectionService;
    @Resource
    private ICourseService courseService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 监听选课对列
     * @param str 选课信息
     */
    @JmsListener(destination = SELECT_QUEUE_NAME)
    private void getSelectMsg(String str) {
        Section section = JSONUtil.toBean(str, Section.class);
        sectionService.addSection(section);
        courseService.incrementCosNow(section.getSecCos());
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(COURSES_REDIS_KEY);
        Map<String, String> map = entries
                .values()
                .stream()
                .map(o -> (String) o).map(json -> JSONUtil.toBean(json, Course.class))
                .peek(course -> {
                    if (section.getSecCos().equals(course.getCosId())) {
                        course.setCosNow(course.getCosNow() + 1);
                    }
                })
                .collect(Collectors.toMap(course -> course.getCosId().toString(), JSONUtil::toJsonStr));
        stringRedisTemplate.opsForHash().putAll(COURSES_REDIS_KEY, map);
        stringRedisTemplate.expire(COURSES_REDIS_KEY, COURSES_REDIS_TTL, MINUTES);
    }

    /**
     * 监听退课对列
     * @param str 退课信息
     */
    @JmsListener(destination = CANCEL_QUEUE_NAME)
    private void getCancelMsg(String str) {
        Section section = JSONUtil.toBean(str, Section.class);
        sectionService.removeSection(section);
        courseService.decrementCosNow(section.getSecCos());
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(COURSES_REDIS_KEY);
        Map<String, String> map = entries
                .values()
                .stream()
                .map(o -> (String) o).map(json -> JSONUtil.toBean(json, Course.class))
                .peek(course -> {
                    if (section.getSecCos().equals(course.getCosId())) {
                        course.setCosNow(course.getCosNow() - 1);
                    }
                })
                .collect(Collectors.toMap(course -> course.getCosId().toString(), JSONUtil::toJsonStr));
        stringRedisTemplate.opsForHash().putAll(COURSES_REDIS_KEY, map);
        stringRedisTemplate.expire(COURSES_REDIS_KEY, COURSES_REDIS_TTL, MINUTES);
    }

}
