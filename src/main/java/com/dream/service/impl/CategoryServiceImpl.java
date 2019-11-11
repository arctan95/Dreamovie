package com.dream.service.impl;

import com.dream.common.E3Result;
import com.dream.mapper.CategoryMapper;
import com.dream.po.Category;
import com.dream.po.CategoryExample;
import com.dream.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName CategoryServiceImpl
 * @Description TODO
 * @Author tan
 * @Date 2019/11/10 22:20
 * @Version 1.0
 **/
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public E3Result GetAllCategory() {
        CategoryExample example = new CategoryExample();
        CategoryExample.Criteria criteria = example.createCriteria();
        // 执行查询
        List<Category> list = categoryMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return E3Result.build(400, "获取分类标签错误");
        }
        return E3Result.ok(list);
    }
}
