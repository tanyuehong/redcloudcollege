package com.redskt.collegeservice.controller;


import com.redskt.collegeservice.entity.EduTeacher;
import com.redskt.collegeservice.service.EduTeacherService;
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
@RestController
@RequestMapping("/collegeservice/edu-teacher")
public class EduTeacherController {
    // 把service注入
    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("findAll")
    public List<EduTeacher> findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return list;
    }
}

