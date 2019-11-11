package com.dream.service;

import com.dream.po.Movie;

import java.util.List;

/**
 * @ClassName AlsService
 * @Description TODO
 * @Author tan
 * @Date 2019/11/10 17:02
 * @Version 1.0
 **/
public interface AlsService {
    List<Movie> selectAlsMoviesByUserId(Integer userid);
}
