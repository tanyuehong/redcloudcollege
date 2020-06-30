package com.redskt.collegeservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redskt.collegeservice.entity.admin.EduCourse;
import com.redskt.collegeservice.entity.front.vo.CourseFrontVo;
import com.redskt.collegeservice.entity.front.vo.CourseWebVo;
import com.redskt.collegeservice.entity.subject.ChapterVo;
import com.redskt.collegeservice.entity.subject.SubjectOne;
import com.redskt.collegeservice.service.EduChapterService;
import com.redskt.collegeservice.service.EduCourseService;
import com.redskt.collegeservice.service.EduSubjectService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home/course")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class EduCourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduSubjectService subjectService;

    //1 条件查询带分页查询课程
    @PostMapping("getCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        //返回分页所有数据
        return R.ok().data(map);
    }

    //2 课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
    }

    //课程分类列表（树形）
    @PostMapping("getAllSubject")
    public R getAllSubject() {
        //list集合泛型是一级分类
        List<SubjectOne> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}
