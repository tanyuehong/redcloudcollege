package com.redskt.collegeservice.mapper;

import com.redskt.collegeservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.collegeservice.entity.query.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-05
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getPublishCourseInfo(String id);
}
