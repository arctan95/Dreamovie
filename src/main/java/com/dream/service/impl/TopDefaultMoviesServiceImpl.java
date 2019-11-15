package com.dream.service.impl;

import com.dream.mapper.TopdefaultmoviesMapper;
import com.dream.po.Movie;
import com.dream.service.TopDefaultMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TopDefaultMoviesServiceImpl
 * @Description TODO
 * @Author tan
 * @Date 2019/11/5 21:36
 * @Version 1.0
 **/
@Service
public class TopDefaultMoviesServiceImpl implements TopDefaultMovieService {
    @Autowired
    private TopdefaultmoviesMapper topdefaultmoviesMapper;

    @Override
    public List<Movie> SelectRegDefaultMovie() {
        List<Movie> list = topdefaultmoviesMapper.selectRegDefaultMovie();
        return list;
    }
}
