package com.lee.service;

import com.lee.pojo.Section;

/**
 * Section表服务接口
 *
 * @author Lee
 * @since 2022 -05-14 21:42:29
 */
public interface ISectionService {

    /**
     * 删除对应的section
     *
     * @param section section实体
     */
    void removeSection(Section section);

    /**
     * 增加section
     *
     * @param section section实体
     */
    void addSection(Section section);

}
