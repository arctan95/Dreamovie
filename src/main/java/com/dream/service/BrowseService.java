package com.dream.service;

import com.dream.po.Browse;

/**
 * @ClassName BrowseService
 * @Description TODO
 * @Author tan
 * @Date 2019/11/14 20:39
 * @Version 1.0
 **/
public interface BrowseService {
    // 根据用户id获取Browse记录
    Browse getBrowseByUserId(Integer userid);
}
