package com.dream.mapper;

import com.dream.po.Browse;
import com.dream.po.BrowseExample;
import com.dream.po.Selectquery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrowseMapper {
    int countByExample(BrowseExample example);

    int deleteByExample(BrowseExample example);

    int deleteByPrimaryKey(Integer browseid);

    int insert(Browse record);

    int insertSelective(Browse record);

    List<Browse> selectByExample(BrowseExample example);

    Browse selectByPrimaryKey(Integer browseid);

    int updateByExampleSelective(@Param("record") Browse record, @Param("example") BrowseExample example);

    int updateByExample(@Param("record") Browse record, @Param("example") BrowseExample example);

    int updateByPrimaryKeySelective(Browse record);

    int updateByPrimaryKey(Browse record);

    int insertuserfavourtemovie(Selectquery selectquery);

    int booluserunlikedmovie(int userid, String movieid);
}