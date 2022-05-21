package com.lee.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type R.
 *
 * @author Lee
 * @date 2020 -05-12 16:59
 */
@Data
@AllArgsConstructor
public class R {

    /**
     * 业务执行是否成功
     */
    private boolean status;
    /**
     * 业务执行结果
     */
    private Object result;
    /**
     * 业务执行附加消息
     */
    private String message;

    /**
     * 返回成功结果
     * @param result  result
     * @param message message
     * @return 结果
     */
    public static R ok(Object result, String message) {
        return new R(true, result, message);
    }

    /**
     * Ok r.
     *
     * @return the r
     */
    public static R ok() {
        return new R(true, null, null);
    }

    /**
     * Ok r.
     *
     * @param message the message
     * @return the r
     */
    public static R ok(String message) {
        return new R(true, null, message);
    }

    /**
     * Ok r.
     *
     * @param result the result
     * @return the r
     */
    public static R ok(Object result) {
        return new R(true, result, null);
    }

    /**
     * 返回错误结果消息
     * @param result  result
     * @param message message
     * @return 结果
     */
    public static R error(Object result, String message) {
        return new R(false, result, message);
    }

    /**
     * Error r.
     *
     * @return the r
     */
    public static R error() {
        return new R(false, null, null);
    }

    /**
     * Error r.
     *
     * @param message the message
     * @return the r
     */
    public static R error(String message) {
        return new R(false, null, message);
    }

}
