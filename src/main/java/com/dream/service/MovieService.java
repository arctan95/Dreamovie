package com.dream.service;

import com.dream.common.E3Result;
import com.dream.po.Movie;
import com.dream.po.Selectquery;

import java.util.List;

/**
 * @ClassName MovieService
 * @Description TODO
 * @Author tan
 * @Date 2019/11/10 20:12
 * @Version 1.0
 **/
public interface MovieService {
    //选择默认的电影5部(暂时用于推荐表中用户推荐电影不足五部的时候增加)
    E3Result SelectTopDefaultMovie(int limit);
    //电影排序分类选择20部一次
    E3Result SortMoiveBycategory(Selectquery query);
    //搜索电影by id
    E3Result SortMoiveByMovieid(int id);
    //相似电影10部
    E3Result Select5SimilarMoviesById(int id);
    //判断用户对电影的喜爱
    int booluserunlikedmovie(int userid,String movieid);
    void InsertUserFavouriteMoive(Selectquery selectquery);//用户收藏电影
    //好像可以合并？和上面一个方法同样的功能
    Movie getMovieByMovieid(Integer id);
    //电影名称搜索电影排序时用
    List<Movie> selectMoviesByName(String moviename);
    //用户评价将相似电影写入recab
    String Select5SimilarMovies(int id);
}
