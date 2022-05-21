package com.lee.utils;

/**
 * The type Constants.
 *
 * @author Lee
 */
public class Constants {

    /**
     * The constant STUDENT_REDIS_PREFIX.
     */
    public static final String STUDENT_REDIS_PREFIX = "login:student:";
    /**
     * The constant SELECTED_REDIS_PREFIX.
     */
    public static final String SELECTED_REDIS_PREFIX = "selected_courses:";
    /**
     * The constant COURSES_REDIS_KEY.
     */
    public static final String COURSES_REDIS_KEY = "courses";
    /**
     * The constant COURSE_SUM_REDIS_KEY.
     */
    public static final String COURSE_SUM_REDIS_KEY = "course:sum";
    /**
     * The constant SELECT_QUEUE_NAME.
     */
    public static final String SELECT_QUEUE_NAME = "select";
    /**
     * The constant CANCEL_QUEUE_NAME.
     */
    public static final String CANCEL_QUEUE_NAME = "cancel";
    /**
     * The constant STUDENT_REDIS_TTL.
     */
    public static final Integer STUDENT_REDIS_TTL = 30;
    /**
     * The constant COURSES_REDIS_TTL.
     */
    public static final Integer COURSES_REDIS_TTL = 30;
    /**
     * The constant COURSE_SUM_REDIS_TTL.
     */
    public static final Integer COURSE_SUM_REDIS_TTL = 30;
    /**
     * The constant SELECTED_REDIS_TTL.
     */
    public static final Integer SELECTED_REDIS_TTL = 30;
    /**
     * The constant LOGIN_ERR_MESSAGE.
     */
    public static final String LOGIN_ERR_MESSAGE = "登陆失败，请检查用户名或者密码是否正确";
    /**
     * The constant SELECT_ERR_MESSAGE.
     */
    public static final String SELECT_ERR_MESSAGE = "课程已满";
    /**
     * The constant GLOBAL_ERR.
     */
    public static final String GLOBAL_ERR = "错误！！！";

}
