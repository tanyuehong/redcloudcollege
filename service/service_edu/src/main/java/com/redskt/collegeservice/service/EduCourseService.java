package com.redskt.collegeservice.service;

import com.redskt.collegeservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.collegeservice.entity.query.CourseInfoVo;
import com.redskt.collegeservice.entity.query.CoursePublishVo;

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
}
