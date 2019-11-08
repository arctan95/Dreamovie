package com.dream.service.impl;

import com.dream.common.E3Result;
import com.dream.mapper.UserMapper;
import com.dream.po.User;
import com.dream.po.UserExample;
import com.dream.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * @ClassName LoginServiceImpl
 * @Description TODO
 * @Author tan
 * @Date 2019/11/8 22:20
 * @Version 1.0
 **/
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public E3Result userLogin(String username, String password) {
        // 1.判断用户和密码是否正确
        // 根据用户名查询用户信息
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        // 执行查询
        List<User> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            // 返回登陆失败
            return E3Result.build(400, "用户名或密码错误");
        }

        // 取用户信息
        User user = list.get(0);
        // 判断密码是否正确
        if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            // 2.如果不正确，返回登录失败
            return E3Result.build(400, "用户名或密码错误");
        } else {
            return E3Result.ok(user);
        }
    }
}