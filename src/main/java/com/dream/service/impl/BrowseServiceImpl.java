package com.dream.service.impl;

import com.dream.mapper.BrowseMapper;
import com.dream.po.Browse;
import com.dream.po.BrowseExample;
import com.dream.service.BrowseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName BrowseServiceImpl
 * @Description TODO
 * @Author tan
 * @Date 2019/11/14 20:39
 * @Version 1.0
 **/
public class BrowseServiceImpl implements BrowseService {

    @Autowired
    private BrowseMapper browseMapper;

    @Override
    public Browse getBrowseByUserId(Integer userid) {
        BrowseExample example = new BrowseExample();
        BrowseExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(userid);
        List<Browse> browseList = browseMapper.selectByExample(example);
        if(browseList.size() != 0) {
            return browseList.get(0);
        } else {
            return null;
        }
    }
}
