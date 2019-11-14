package com.dream.service;

import com.dream.po.Review;

import java.util.List;

/**
 * @ClassName ReviewService
 * @Description TODO
 * @Author tan
 * @Date 2019/11/14 20:33
 * @Version 1.0
 **/
public interface ReviewService {
    // 进入个人中心前获取其全部的影评
    List<Review> getReviewListByUserId(Integer id);
}
