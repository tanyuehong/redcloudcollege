package com.redskt.classroom.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.RedClassCourse;
import com.redskt.classroom.entity.vo.RedClassCourseWebVo;
import com.redskt.classroom.entity.vo.RedClassCourseFrontVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-05
 */
public interface RedCourseService extends IService<RedClassCourse> {

    RedClassCourseWebVo getBaseCourseInfo(String courseId);

    List<RedClassCourseWebVo> getCourseList();

    Map<String, Object> getCourseFrontList(Page<RedClassCourse> pageCourse, RedClassCourseFrontVo courseFrontVo);

}
