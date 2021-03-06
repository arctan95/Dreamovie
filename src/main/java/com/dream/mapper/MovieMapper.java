package com.dream.mapper;

import com.dream.po.Movie;
import com.dream.po.MovieExample;
import com.dream.po.Selectquery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName MovieMapper
 * @Description TODO
 * @Author tan
 * @Date 2019/11/10 17:05
 * @Version 1.0
 **/
public interface MovieMapper {
    int countByExample(MovieExample example);

    int deleteByExample(MovieExample example);

    int deleteByPrimaryKey(Integer movieid);

    int insert(Movie record);

    int insertSelective(Movie record);

    List<Movie> selectByExample(MovieExample example);

    List<Movie> selectBycategory(Selectquery query);

    Movie selectByPrimaryKey(Integer movieid);

    int updateByExampleSelective(@Param("record") Movie record, @Param("example") MovieExample example);

    int updateByExample(@Param("record") Movie record, @Param("example") MovieExample example);

    int updateByPrimaryKeySelective(Movie record);

    int updateByPrimaryKey(Movie record);

    List<Movie> SelectTopDefaultMovie(int limit);

    List<Movie> selectByNameLike(String moviename);
}
