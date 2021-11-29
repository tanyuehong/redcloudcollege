package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.vo.OPHomeGuessLikeVo;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/home")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class RedIndexController {
    @Autowired
    private RedCourseService courseService;

    @Autowired
    private RedBannerService bannerService;

    @Autowired
    private OpBlogDetailService blogService;

    @Autowired
    private EduTechnologyBookService bookService;

    //查询前8条热门课程，查询前4条名师
    @GetMapping("index")
    public R index() {
        //查询前8条热门课程
        QueryWrapper<RedClassCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<RedClassCourse> eduList = courseService.list(wrapper);

        Random random = new Random();

        // 猜你喜欢的内容

        // 1.课程的随机个数
        int courseC = random.nextInt(4);
        if (courseC == 0) {
            courseC = 1;
        }
        QueryWrapper<RedClassCourse> videowrapper = new QueryWrapper<>();
        videowrapper.orderByDesc("readcount");
        videowrapper.last(String.format("limit %d",courseC));
        List<RedClassCourse> guessvideo = courseService.list(videowrapper);

        // 2. 博文的个数
        int blogCunt = random.nextInt(4);
        if (blogCunt == 0) {
            blogCunt = 1;
        }
        QueryWrapper<OpBlogDetail> blogDetailQueryWrapper = new QueryWrapper<>();
        blogDetailQueryWrapper.select("id","title","type","good","faver","readcount","price","descrb","hot");
        blogDetailQueryWrapper.orderByDesc("hot");
        blogDetailQueryWrapper.last(String.format("limit %d",blogCunt));
        List<OpBlogDetail> blogList = blogService.list(blogDetailQueryWrapper);

        // 3。技术书籍的个数
        int bookCount = random.nextInt(4);
        if (bookCount == 0) {
            bookCount = 1;
        }
        QueryWrapper<EduTechnologyBook> bookwrapper = new QueryWrapper<>();
        bookwrapper.orderByDesc("viewCount");
        bookwrapper.last(String.format("limit %d",bookCount));
        List<EduTechnologyBook> bookList = bookService.list(bookwrapper);


        List<OPHomeGuessLikeVo> gussLike = new ArrayList<>();

        while (guessvideo.size()>0 || blogList.size()>0 || bookList.size()>0) {
            int type = random.nextInt(3);
            OPHomeGuessLikeVo vo = new OPHomeGuessLikeVo();
            if (type == 0 && guessvideo.size()>0) {


            } else if()

        }

        for (int i=0;i<blogList.size();i++) {



            OpBlogDetail detail = blogList.get(i);
            if (detail.getDescrb().length()>150) {
                detail.setDescrb(detail.getDescrb().substring(0,150)+"...");
            }
        }
        List<RedClassBanner> banerList = bannerService.selectAllBanner();
        return R.ok().data("banerList",banerList).data("eduList",eduList).data("teacherList",teacherList);
    }
}
