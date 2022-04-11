package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.vo.*;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import com.redskt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home/course")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class RedCourseController {

    @Autowired
    private RedCourseService courseService;

    @Autowired
    private RedChapterService chapterService;

    @Autowired
    private RedSubjectService subjectService;

    @Autowired
    private RedUserService userService;

    @Autowired
    private OpBlogDetailService blogDetailService;

    @Autowired
    private EduTechnologyBookService bookService;

    @Autowired
    private RedUserFocusService focusService;

    @GetMapping("getCourseIndex")
    public R getCourseIndex() {
        Page<RedClassCourse> pageCourse = new Page<>(1,8);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,new RedClassCourseFrontVo());
        //返回分页所有数据
        List<RedClassSubjectOneVo> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("coursList",map).data("list",list);
    }

    //1 条件查询带分页查询课程
    @PostMapping("getCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) RedClassCourseFrontVo courseFrontVo) {
        Page<RedClassCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        //返回分页所有数据
        return R.ok().data(map);
    }

    //2 课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId) {
        //根据课程id，编写sql语句查询课程信息
        RedClassCourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<RedClassChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
    }

    // 获取老师详情
    @GetMapping("getTeacherDetail/{teacherId}")
    public R getTeacherDetail(@PathVariable String teacherId) {
        RedClassUserVo teacher = userService.getUserInfoFocusCount(teacherId);
        if(teacher != null) {
            QueryWrapper<RedClassCourse> wrapper = new QueryWrapper<>();
            wrapper.eq("teacher_id", teacherId);
            wrapper.last("limit 6");
            List<RedClassCourse> courseList = courseService.list(wrapper);

            QueryWrapper<RedClassBook> bookWarp = new QueryWrapper<>();
            bookWarp.eq("auid",teacherId);
            bookWarp.last("limit 6");
            List<RedClassBook> bookList = bookService.list(bookWarp);

            QueryWrapper<OpBlogDetail> blogWarp = new QueryWrapper<>();
            blogWarp.eq("auid",teacherId);
            blogWarp.last("limit 6");
            List<OpBlogDetail> blogDetailList = blogDetailService.list(blogWarp);
            return R.ok().data("teacher", teacher).data("courseList", courseList).data("bookList",bookList).data("praticeList",blogDetailList);
        }
        return R.error("没有对应的老师信息哈");
    }

    @GetMapping("getUserFocus/{fId}")
    public R getUserGoodState(@PathVariable String fId, HttpServletRequest request) {
        if (fId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                QueryWrapper<RedUserFocus> focusWrapper = new QueryWrapper<>();
                focusWrapper.eq("uid", uId);
                focusWrapper.eq("fid", fId);
                if (focusService.list(focusWrapper).size()>0) {
                    return R.ok().data("focus", true);
                } else {
                    return R.ok().data("focus", false);
                }
            }
        }
        return R.ok().data("focus", false);
    }
}