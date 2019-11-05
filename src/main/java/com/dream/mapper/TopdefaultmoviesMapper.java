package com.dream.mapper;

import com.dream.po.Movie;
import com.dream.po.Topdefaultmovies;
import com.dream.po.TopdefaultmoviesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName TopdefalultmoviesMapper
 * @Description TODO
 * @Author tan
 * @Date 2019/11/5 21:43
 * @Version 1.0
 **/
public interface TopdefaultmoviesMapper {

    int countByExample(TopdefaultmoviesExample var1);

    int deleteByExample(TopdefaultmoviesExample var1);

    int deleteByPrimaryKey(Integer var1);

    int insert(Topdefaultmovies var1);

    int insertSelective(Topdefaultmovies var1);

    List<Topdefaultmovies> selectByExample(TopdefaultmoviesExample var1);

    Topdefaultmovies selectByPrimaryKey(Integer var1);

    int updateByExampleSelective(@Param("record") Topdefaultmovies var1, @Param("example") TopdefaultmoviesExample var2);

    int updateByExample(@Param("record") Topdefaultmovies var1, @Param("example") TopdefaultmoviesExample var2);

    int updateByPrimaryKeySelective(Topdefaultmovies var1);

    int updateByPrimaryKey(Topdefaultmovies var1);

    List<Movie> selectRegDefaultMovie();
}
