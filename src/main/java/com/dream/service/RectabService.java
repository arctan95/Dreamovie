package com.dream.service;

import com.dream.po.Rectab;

/**
 * @ClassName RectabService
 * @Description TODO
 * @Author tan
 * @Date 2019/11/10 17:20
 * @Version 1.0
 **/
public interface RectabService {
    // 根据用户id获取推荐电影信息
    Rectab getRectabByUserId(Integer userid);
    int insert(Rectab rectab);
    void update(Rectab rectab);
}
