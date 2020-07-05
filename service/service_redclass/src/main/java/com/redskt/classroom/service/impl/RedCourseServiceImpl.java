package com.redskt.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redskt.classroom.entity.RedClassCourse;
import com.redskt.classroom.mapper.RedCourseMapper;
import com.redskt.classroom.service.RedCourseService;
import com.redskt.servicebase.excepionhandler.RedCloudCollegeExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-05
 */
@Service
public class RedCourseServiceImpl extends ServiceImpl<RedCourseMapper, RedClassCourse> implements RedCourseService {


}
