package com.dream.service.impl;

import com.dream.common.E3Result;
import com.dream.mapper.BrowseMapper;
import com.dream.mapper.MovieMapper;
import com.dream.mapper.SimilartabMapper;
import com.dream.po.*;
import com.dream.service.MovieService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MovieServiceImpl
 * @Description TODO
 * @Author tan
 * @Date 2019/11/10 20:20
 * @Version 1.0
 **/
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private SimilartabMapper similartabMapper;

    @Autowired
    private BrowseMapper browseMapper;


    @Override
    public E3Result SelectTopDefaultMovie(int limit) {
        List<Movie> list = movieMapper.SelectTopDefaultMovie(limit);
        if (list == null || list.size() == 0) {
            return E3Result.build(400, "获取电影信息错误");
        }
        return E3Result.ok(list);
    }

    @Override
    public E3Result SortMoiveBycategory(Selectquery query) {
        MovieExample example = new MovieExample();
        MovieExample.Criteria criteria = example.createCriteria();
        // 执行查询
        List<Movie> list = movieMapper.selectBycategory(query);
        return E3Result.ok(list);
    }

    @Override
    public E3Result SortMoiveByMovieid(int id) {
        // 执行查询
        Movie movie = movieMapper.selectByPrimaryKey(id);
        return E3Result.ok(movie);
    }

    @Override
    public E3Result Select5SimilarMoviesById(int id) {
        SimilartabExample similartabExample = new SimilartabExample();
        SimilartabExample.Criteria criteria = similartabExample.createCriteria();
        criteria.andItemid1EqualTo(id);
        similartabExample.setOrderByClause("similar");
        List<Similartab> list = similartabMapper.select5ByExample(similartabExample);
        List<Movie> movieList = new ArrayList<>();
        Movie movie = null;
        for(Similartab similartab : list) {
            movie = movieMapper.selectByPrimaryKey(similartab.getItemid2());
            if (movie != null) {
                movieList.add(movie);
            }
        }
        return E3Result.ok(movieList);
    }

    @Override
    public int booluserunlikedmovie(int userid,String movieid)
    {
        return  browseMapper.booluserunlikedmovie(userid,movieid);
    }
    @Override
    public void InsertUserFavouriteMoive(Selectquery selectquery)
    {
        browseMapper.insertuserfavourtemovie(selectquery);
    }
    @Override
    public Movie getMovieByMovieid(Integer id) {
        Movie movie = movieMapper.selectByPrimaryKey(id);
        return movie;
    }
    @Override
    public List<Movie> selectMoviesByName(String moviename){
        List<Movie> list = movieMapper.selectByNameLike(moviename);
        return list;
    }
    @Override
    public String Select5SimilarMovies(int id) {
        SimilartabExample similartabExample = new SimilartabExample();
        SimilartabExample.Criteria criteria = similartabExample.createCriteria();
        criteria.andItemid1EqualTo(id);
        similartabExample.setOrderByClause("similar");
        List<String> movieStr = similartabMapper.select5ByExampleStr(similartabExample);
        String movieRet = StringUtils.join(movieStr, ",");
        return movieRet;
    }
}
