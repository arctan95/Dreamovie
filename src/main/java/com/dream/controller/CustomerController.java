package com.dream.controller;

import com.dream.common.E3Result;
import com.dream.po.Movie;
import com.dream.po.User;
import com.dream.service.RegisterService;
import com.dream.service.TopDefaultMovieService;
import com.dream.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.List;

/**
 * @ClassName CustomerController
 * @Description TODO
 * @Author tan
 * @Date 2019/11/5 19:55
 * @Version 1.0
 **/
public class CustomerController {
    @Autowired
    private UserService userService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private TopDefaultMovieService topDefaultMovieService;

    // 进入注册页面
    @RequestMapping("/page/register")
    public String reg(HttpServletRequest request) {
        // 选择topmovies给用户选择他喜欢的电影
        List<Movie> list = topDefaultMovieService.SelectRegDefaultMovie();
        request.getSession().setAttribute("TopRegDefaultMovie", list);
        return "register";
    }

    // 进入登录页面
    @RequestMapping("/page/login")
    public String log() {return "login";}

    // 检查用户名/邮箱是否符合规范（在没有点击注册按钮前检查）
    @RequestMapping("/customer/check/{param}/{type}")
    @ResponseBody
    public E3Result checkData(@PathVariable String param, @PathVariable Integer type) {
        // 后端decode解码（如果前端输入的是中文）
        try {
            String str = URLDecoder.decode("UTF-8");
            E3Result e3Result = registerService.checkData(str, type);
            return e3Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 对用户进行注册（在全部检查完成后）
    @RequestMapping(value = "/customer/register", method = RequestMethod.POST)
    @ResponseBody
    public E3Result register(User user, HttpServletRequest request) {
        // 返回用户id，用于用户选择喜欢的电影后把相应信息存browser表
        Integer userId = 0;
        E3Result e3Result = registerService.register(user);
        if (e3Result.getStatus() == 200) {
            userId = (Integer)e3Result.getData();
        }
        request.getSession().setAttribute("userId", userId);
        return e3Result;
    }
}
