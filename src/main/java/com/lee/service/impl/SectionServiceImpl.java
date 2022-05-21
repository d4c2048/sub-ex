package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.pojo.Section;
import com.lee.dao.SectionDao;
import com.lee.service.ISectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Section表服务实现类
 *
 * @author Lee
 * @since 2022 -05-14 21:42:29
 */
@Service("sectionService")
@Transactional(rollbackFor = Exception.class)
public class SectionServiceImpl implements ISectionService {

    @Resource
    private SectionDao sectionDao;

    @Override
    public void removeSection(Section section) {
        sectionDao.delete(new QueryWrapper<>(section));
    }

    @Override
    public void addSection(Section section) {
        sectionDao.insert(section);
    }

}
