package com.dream.service.impl;

import com.dream.mapper.ReviewMapper;
import com.dream.po.Review;
import com.dream.po.ReviewExample;
import com.dream.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName ReviewServiceImpl
 * @Description TODO
 * @Author tan
 * @Date 2019/11/14 20:35
 * @Version 1.0
 **/
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public List<Review> getReviewListByUserId(Integer id) {
        ReviewExample example = new ReviewExample();
        ReviewExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(id);
        List<Review> reviews = reviewMapper.selectByExample(example);
        return reviews;
    }
}
