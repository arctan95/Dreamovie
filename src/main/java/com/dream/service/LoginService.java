package com.dream.service;

import com.dream.common.E3Result;

/**
 * @ClassName LoginService
 * @Description TODO
 * @Author tan
 * @Date 2019/11/8 22:20
 * @Version 1.0
 **/
public interface LoginService {
    //登录
    E3Result userLogin(String username, String password);
}
