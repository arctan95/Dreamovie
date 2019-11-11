package com.dream.controller;

import com.dream.common.E3Result;
import com.dream.common.JsonUtils;
import com.dream.po.*;
import com.dream.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author tan
 * @Date 2019/11/5 17:18
 * @Version 1.0
 **/

@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AlsService alsService;

    @Autowired
    private RectabService rectabService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private StarService starService;

    @RequestMapping("/")
    public String showHomepage(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        // 用户登录则推荐他的电影否则推荐默认电影（固定五部）
        if (user != null) {
            List<Movie> movies = new ArrayList<>();
            // 从ALS表中查询推荐强度8以上的电影
            List<Movie> alsMovies = alsService.selectAlsMoviesByUserId(user.getUserid());
            for (Movie alsMovie : alsMovies) {
                movies.add(alsMovie);
            }

            Rectab rectab = rectabService.getRectabByUserId(user.getUserid());
            if (rectab != null && null != rectab.getMovieids()) {
                String movieids = rectab.getMovieids();
                String[] strmovieids = movieids.split(",");
                int i = 0;
                for (String strmovieid : strmovieids) {
                    if (i == 5) {
                        break;
                    }
                    Integer movieid = Integer.parseInt(strmovieid);
                    Movie movie = movieService.getMovieByMovieid(movieid);
                    if (movie != null)
                        movies.add(movie);
                    i++;
                }
            }
            // 不足五部从默认电影中凑齐五部
            if (movies.size() < 5) {
                E3Result TopDefaultMovie = movieService.SelectTopDefaultMovie(5 - movies.size());
                List<Movie> temp = (List<Movie>) TopDefaultMovie.getData();
                movies.addAll(temp);
            }

            // 将电影信息放在map中转Json再进入session给前端 map中存放movieid
            request.getSession().setAttribute("TopDefaultMovie", movies);
            Map moviemap = new HashMap();
            for (int i = 0; i < movies.size(); i++) {
                moviemap.put(movies.get(i).getMovieid().toString(), i);
            }
            request.getSession().setAttribute("TopDefaultMovieMap", JsonUtils.objectToJson(moviemap));
        } else {
            E3Result TopDefaultMovie = movieService.SelectTopDefaultMovie(5);
            List<Movie> list = (List<Movie>) TopDefaultMovie.getData();
            request.getSession().setAttribute("TopDefaultMovie", list);
            Map moviemap = new HashMap();
            for (int i = 0; i < list.size(); i++) {
                moviemap.put(list.get(i).getMovieid().toString(), i);
            }
            request.getSession().setAttribute("TopDefaultMovieMap", JsonUtils.objectToJson(moviemap));
        }
        return "Home";
    }

    // 选电影界面
    @RequestMapping("/index")
    public String showIndex(HttpServletRequest request) {
        // 获取所有分类标签
        E3Result e3ResultAllCategory = categoryService.GetAllCategory();
        List<Category> list1 = (List<Category>) e3ResultAllCategory.getData();
        // 获取所有电影数据（缺少筛选，默认一次加载20个）
        Integer Categoryid = 0;
        Selectquery query = new Selectquery();
        query.setCategoryid(Categoryid);
        query.setmolimit(0);
        query.setSort("numrating");
        E3Result e3ResultAllMovie = movieService.SortMoiveBycategory(query);
        List<Movie> list2 = (List<Movie>) e3ResultAllMovie.getData();
        // 设置session
        request.getSession().setAttribute("category", list1);
        request.getSession().setAttribute("movie", list2);
        return "index";
    }


    // 电影详情传值
    @RequestMapping("/Customer/Description")
    @ResponseBody
    public String GoMovieDescription(HttpServletRequest request) {
        request.getSession().removeAttribute("booluserunlikedmovie");
        // 获取用户点击的movieid
        int movieid = Integer.parseInt(request.getParameter("id"));
        E3Result e3Result1 = movieService.SortMoiveByMovieid(movieid);
        // 用户所点击的电影详情movie
        Movie movie = (Movie) e3Result1.getData();
        User user = (User) request.getSession().getAttribute("user");
        // 判断用户是否登录以及对这部电影的喜爱
        if (user != null) {
            E3Result e3Result2 = starService.SortReviewByUseridandMovieid(user.getUserid(), movieid);
            Review review = (Review) e3Result2.getData();
            request.getSession().setAttribute("userstar", review);
            // 判断登录用户是否喜欢该电影
            int booluserlikedmovie = movieService.booluserunlikedmovie(user.getUserid(), request.getParameter("id"));
            request.getSession().setAttribute("booluserunlikedmovie", booluserlikedmovie);
        } else {
            Review review = null;
            request.getSession().setAttribute("userstar", review);
        }
        // 设置session
        request.getSession().setAttribute("moviedescription", movie);
        return "success";
    }
}
