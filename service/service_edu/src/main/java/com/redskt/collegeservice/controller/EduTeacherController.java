package com.redskt.collegeservice.controller;


import com.redskt.commonutils.R;
import com.redskt.collegeservice.entity.EduTeacher;
import com.redskt.collegeservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2020-04-26
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/collegeservice/edu-teacher")
public class EduTeacherController {
    // 把service注入
    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }
}

