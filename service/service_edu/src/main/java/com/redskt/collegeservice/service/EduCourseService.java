package com.redskt.collegeservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redskt.collegeservice.entity.admin.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.collegeservice.entity.front.vo.CourseFrontVo;
import com.redskt.collegeservice.entity.front.vo.CourseWebVo;
import com.redskt.collegeservice.entity.query.CourseInfoVo;
import com.redskt.collegeservice.entity.query.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-05
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
