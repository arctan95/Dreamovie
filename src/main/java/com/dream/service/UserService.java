package com.dream.service;

import com.dream.po.User;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author tan
 * @Date 2019/11/5 19:59
 * @Version 1.0
 **/
public interface UserService {
    // 编辑用户
    public User getUserById(Integer id);

    // 更新用户信息用于修改密码
    public void updateUser(Integer userid, String password, String email);
}
