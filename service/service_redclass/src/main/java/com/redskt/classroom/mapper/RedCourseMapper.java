package com.redskt.classroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.RedClassCourse;
import com.redskt.classroom.entity.vo.RedClassCourseWebVo;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-05
 */
public interface RedCourseMapper extends BaseMapper<RedClassCourse> {
    RedClassCourseWebVo getBaseCourseInfo(String courseId);

    List<RedClassCourseWebVo> getCourseList();
}
