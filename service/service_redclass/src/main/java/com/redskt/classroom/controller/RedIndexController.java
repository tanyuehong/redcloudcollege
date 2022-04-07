package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.vo.OPHomeGuessLikeVo;
import com.redskt.classroom.entity.vo.RedClassCourseWebVo;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private EduBookChaptersService chaperService;

    @Autowired
    private RedTeacherService teacherService;

    //查询前8条热门课程，查询前4条名师
    @GetMapping("index")
    public R index() {
        //查询前8条热门课程
        List<RedClassCourseWebVo> eduList = courseService.getCourseList();
        Random random = new Random();
        // 猜你喜欢的内容

        // 1.课程的随机个数
        int courseC = random.nextInt(4);
        if (courseC == 0) {
            courseC = 1;
        }
        QueryWrapper<RedClassCourse> videowrapper = new QueryWrapper<>();
        videowrapper.orderByDesc("view_count");
        videowrapper.last(String.format("limit %d",courseC));
        List<RedClassCourse> guessvideo = courseService.list(videowrapper);

        // 2. 博文的个数
        int blogCunt = random.nextInt(4);
        if (blogCunt == 0) {
            blogCunt = 1;
        }
        QueryWrapper<OpBlogDetail> blogDetailQueryWrapper = new QueryWrapper<>();
        blogDetailQueryWrapper.select("id","title","type","good","faver","view_count","price","descrb","hot");
        blogDetailQueryWrapper.orderByDesc("hot");
        blogDetailQueryWrapper.last(String.format("limit %d",blogCunt));
        List<OpBlogDetail> blogList = blogService.list(blogDetailQueryWrapper);

        // 3。技术书籍的个数
        int bookCount = random.nextInt(4);
        if (bookCount == 0) {
            bookCount = 1;
        }
        QueryWrapper<RedClassBook> bookwrapper = new QueryWrapper<>();
        bookwrapper.orderByDesc("view_count");
        bookwrapper.last(String.format("limit %d",bookCount));
        List<RedClassBook> bookList = bookService.list(bookwrapper);

        List<OPHomeGuessLikeVo> gussLikes = new ArrayList<>();
        int type = random.nextInt(3);
        while (guessvideo.size()>0 || blogList.size()>0 || bookList.size()>0) {
            OPHomeGuessLikeVo vo = new OPHomeGuessLikeVo();
            if (type>2) {
                type = type%3;
            }
            if (type == 0&& guessvideo.size()>0 ) {
                RedClassCourse couseItem = guessvideo.get(0);
                guessvideo.remove(0);
                BeanUtils.copyProperties(couseItem, vo);
                RedClassTeacher teacher = teacherService.getById(couseItem.getTeacherId());
                vo.setContentCount(couseItem.getLessonNum());
                vo.setType(type);
                vo.setAuthor(teacher.getName());
                vo.setAuthorPositon("资深工程师");
                vo.setContent(couseItem.getCdescribe());
                type = random.nextInt(3);
            } else if(type == 1 && blogList.size()>0) {
                OpBlogDetail blog = blogList.get(0);
                blogList.remove(0);
                BeanUtils.copyProperties(blog, vo);
                vo.setType(type);
                vo.setContent(blog.getDescrb());
                type = random.nextInt(3);
            } else if(type == 2 && bookList.size()>0) {
                RedClassBook book = bookList.get(0);
                bookList.remove(0);
                BeanUtils.copyProperties(book, vo);
                QueryWrapper<EduBookChapters> chperWarper = new QueryWrapper<>();
                chperWarper.eq("book_id",book.getId());
                List<EduBookChapters> chapters = chaperService.list(chperWarper);
                vo.setType(type);
                vo.setContent(book.getDescrib());
                vo.setContentCount(chapters.size());
                type = random.nextInt(3);
            } else {
                type++;
                continue;
            }
            gussLikes.add(vo);
        }
        List<RedClassBanner> banerList = bannerService.selectAllBanner();
        return R.ok().data("banerList",banerList).data("eduList",eduList).data("gusslikeList",gussLikes);
    }
}
