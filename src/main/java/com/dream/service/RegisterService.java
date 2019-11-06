package com.dream.service;

import com.dream.common.E3Result;
import com.dream.po.Browse;
import com.dream.po.User;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName RegisterService
 * @Description TODO
 * @Author tan
 * @Date 2019/11/6 20:29
 * @Version 1.0
 **/
public interface RegisterService {

    // 检查输入是否合法实时检测
    public E3Result checkData(String param, int type);

    // 注册用户
    public E3Result register(User user);

    // 注册时候写入用户喜欢的电影
    public void selectFavorite(Browse browse);

    // 点击注册按钮时候检查合法性
    E3Result checkDataBoth(@PathVariable String paramName, @PathVariable String paramEmail, @PathVariable Integer type);
}
