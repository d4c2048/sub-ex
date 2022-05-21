package com.lee.dao;

import com.lee.pojo.Section;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Section表数据库访问层
 *
 * @author Lee
 * @since 2022 -05-14 21:42:29
 */
@Mapper
public interface SectionDao extends BaseMapper<Section> {

}

