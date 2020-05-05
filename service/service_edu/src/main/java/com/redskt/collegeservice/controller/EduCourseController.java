package com.redskt.collegeservice.controller;


import com.redskt.collegeservice.entity.query.CourseInfoVo;
import com.redskt.collegeservice.service.EduCourseService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-05
 */
@RestController
@RequestMapping("/collegeservice/course")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class EduCourseController {


    @Autowired
    private EduCourseService courseService;

    //添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        //返回添加之后课程id，为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

}

