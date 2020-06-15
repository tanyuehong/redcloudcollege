package com.redskt.collegeservice.controller.admin;


import com.redskt.collegeservice.entity.subject.SubjectOne;
import com.redskt.collegeservice.service.EduSubjectService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-05
 */
@RestController
@RequestMapping("/subject")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传过来文件，把文件内容读取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        //上传过来excel文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    //课程分类列表（树形）
    @PostMapping("getAllSubject")
    public R getAllSubject() {
        //list集合泛型是一级分类
        List<SubjectOne> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

