package com.dream.controller;

import com.dream.common.E3Result;
import com.dream.common.JsonUtils;
import com.dream.po.*;
import com.dream.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private ReviewService reviewService;

    @Autowired
    private BrowseService browseService;

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

    // 电影详情界面
    @RequestMapping("/MovieDescription")
    public String showMovieDescription(HttpServletRequest request) {
        return "MovieDescription";
    }

    // 选电影界面加载更多按钮(通过类型标签，时序标签以及现有页面呈现的电影数目三个参数查询)
    @RequestMapping(value = "/loadingmore", method = RequestMethod.POST)
    @ResponseBody
    public E3Result showloadmore(HttpServletRequest request){
        Integer categoryid = Integer.parseInt(request.getParameter("type"));
        int molimit = Integer.parseInt(request.getParameter("molimit"));
        Selectquery query = new Selectquery();
        query.setCategoryid(categoryid);
        query.setmolimit(molimit);
        query.setSort(request.getParameter("sort"));
        E3Result e3ResultAllMovie = movieService.SortMoiveBycategory(query);
        List<Movie> list = (List<Movie>) e3ResultAllMovie.getData();
        E3Result e3Result = E3Result.ok(list);
        return e3Result;
    }

    // 选择排序电影（类型和时序）
    @RequestMapping(value = "/typesortmovie", method = RequestMethod.POST)
    @ResponseBody
    public E3Result showtypesortmovie(HttpServletRequest request) {
        int type = Integer.parseInt(request.getParameter("type"));
        int molimit = Integer.parseInt(request.getParameter("molimit"));
        String sort = request.getParameter("sort");
        Selectquery query = new Selectquery();
        query.setCategoryid(type);
        query.setmolimit(molimit);
        query.setSort(sort);
        E3Result e3ResultAllMovie = movieService.SortMoiveBycategory(query);
        List<Movie> list = (List<Movie>) e3ResultAllMovie.getData();
        E3Result e3Result = E3Result.ok(list);
        return e3Result;
    }

    // 电影评星
    @RequestMapping(value = "/getstar", method = RequestMethod.POST)
    @ResponseBody
    public String getstar(HttpServletRequest request) throws ParseException {
        int userid = Integer.parseInt(request.getParameter("userid"));
        int movieid = Integer.parseInt(request.getParameter("movieid"));
        Double star = Double.parseDouble(request.getParameter("star"));
        if (star >= 3.5) {
            // 查询本地相似表
            String movieids = movieService.Select5SimilarMovies(movieid);
            // 判断数据库是否有该userid
            Rectab rectab = rectabService.getRectabByUserId(userid);
            Rectab rec = new Rectab();
            rec.setUserid(userid);
            rec.setMovieids(movieids);
            // 没有则插入数据库
            if (null == rectab) {
                rectabService.insert(rec);
            } else {
                rectabService.update(rec);
            }
        }

        String str = request.getParameter("time");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = format.parse(str);
        Review review = new Review();
        review.setUserid(userid);
        review.setMovieid(movieid);
        review.setStar(star);
        review.setReviewtime(time);
        // 写入数据库
        starService.setStar(review);
        review = null;
        E3Result e3Result = starService.SortReviewByUseridandMovieid(userid, movieid);
        review = (Review) e3Result.getData();
        // 立即读取影评显示于前端
        request.getSession().setAttribute("userstar", review);
        return "success";
    }


    // 电影详情界面点击相似电影
    @RequestMapping(value = "/getSimiMovies", method = RequestMethod.POST)
    @ResponseBody
    public E3Result getSimiMovies(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        E3Result e3Result = movieService.Select5SimilarMoviesById(id);
        List<Movie> simiMovies = (List<Movie>) e3Result.getData();
        e3Result = e3Result.ok(simiMovies);
        return e3Result;
    }

    // 电影详情界面用户喜欢电影（,id. 格式写入数据库，不存在则插入，存在则更新）
    @RequestMapping(value = "/likedmovie", method = RequestMethod.POST)
    @ResponseBody
    public String likedmovie(HttpServletRequest request) {
        String movieids = "," + request.getParameter("movieid") + ".";
        int userid = Integer.parseInt(request.getParameter("userid"));
        int boollike = Integer.parseInt(request.getParameter("boollike"));
        Selectquery query = new Selectquery();
        query.setCategoryid(userid);
        query.setmolimit(boollike);
        query.setSort(movieids);
        movieService.InsertUserFavouriteMoive(query);
        return "success";
    }

    // 点击个人中心按钮
    @RequestMapping(value = "/page/profile")
    @ResponseBody
    public String goProfile(HttpServletRequest request) {
        // 拿到userid
        User user = (User) request.getSession().getAttribute("user");
        Integer userid = user.getUserid();
        // 影评的电影list
        List<Review> reviews = reviewService.getReviewListByUserId(userid);
        List<Movie> movies = new ArrayList<>();
        // 喜欢的电影list
        Browse browse = browseService.getBrowseByUserId(userid);
        if (browse != null && null != browse.getmovieids()) {
            String movieids = browse.getmovieids().replace(".", "").substring(1);
            String[] strmovieids = movieids.split(",");
            for (String strmovieid : strmovieids) {
                Integer movieid = Integer.parseInt(strmovieid);
                Movie movie = movieService.getMovieByMovieid(movieid);
                movies.add(movie);
            }
        }

        // 为review list中添加电影url
        for (Review review : reviews) {
            Integer movieid = review.getMovieid();
            Movie movie = movieService.getMovieByMovieid(movieid);
            review.setPicture(movie.getPicture());
        }

        request.getSession().setAttribute("movies", movies);
        request.getSession().setAttribute("reviews", reviews);
        return "success";
    }

    // 个人中心按钮
    @RequestMapping("/profile")
    public String showProfile() {
        return "profile";
    }

    // 搜索电影
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public E3Result selectMoviesByName(HttpServletRequest request) {
        String moviename = request.getParameter("search_text");
        if (moviename == null || moviename == "") {
            System.out.println("不能为空");
            return null;
        } else {
            System.out.println("搜索内容" + moviename);
            List<Movie> list = movieService.selectMoviesByName(moviename);
            E3Result e3Result = E3Result.ok(list);
            return e3Result;
        }
    }
}
