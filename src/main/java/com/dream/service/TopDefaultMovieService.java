package com.dream.service;

import com.dream.po.Movie;

import java.util.List;

/**
 * @ClassName TopDefaultMovieService
 * @Description TODO
 * @Author tan
 * @Date 2019/11/5 21:33
 * @Version 1.0
 **/
public interface TopDefaultMovieService {

    // 选择默认的10个电影供用户注册时使用
    public List<Movie> SelectRegDefaultMovie();

}
