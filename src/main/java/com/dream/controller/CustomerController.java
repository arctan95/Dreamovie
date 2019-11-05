package com.dream.controller;

import com.dream.po.Movie;
import com.dream.service.TopDefaultMovieService;
import com.dream.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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


}
