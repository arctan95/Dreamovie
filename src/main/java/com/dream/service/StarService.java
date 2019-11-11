package com.dream.service;

import com.dream.common.E3Result;
import com.dream.po.Review;

/**
 * @ClassName StarService
 * @Description TODO
 * @Author tan
 * @Date 2019/11/11 21:21
 * @Version 1.0
 **/
public interface StarService {
    public void setStar(Review review);
    // 搜索影评，用于用户评价一部电影后立即获取其评价的信息
    E3Result SortReviewByUseridandMovieid(Integer userid, Integer movieid);
}
