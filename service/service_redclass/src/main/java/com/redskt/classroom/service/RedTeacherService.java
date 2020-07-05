package com.redskt.classroom.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.RedClassTeacher;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-04-26
 */
public interface RedTeacherService extends IService<RedClassTeacher> {
    Map<String, Object> getTeacherFrontList(Page<RedClassTeacher> pageTeacher);
}
