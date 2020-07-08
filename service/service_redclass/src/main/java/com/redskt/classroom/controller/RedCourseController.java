package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redskt.classroom.entity.RedClassCourse;
import com.redskt.classroom.entity.vo.RedClassCourseFrontVo;
import com.redskt.classroom.entity.vo.RedClassCourseWebVo;
import com.redskt.classroom.entity.vo.RedClassChapterVo;
import com.redskt.classroom.entity.vo.RedClassSubjectOneVo;
import com.redskt.classroom.service.RedChapterService;
import com.redskt.classroom.service.RedCourseService;
import com.redskt.classroom.service.RedSubjectService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    //课程分类列表（树形）
    @PostMapping("getAllSubject")
    public R getAllSubject() {
        //list集合泛型是一级分类
        List<RedClassSubjectOneVo> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}