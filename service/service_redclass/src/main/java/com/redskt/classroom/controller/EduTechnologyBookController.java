package com.redskt.classroom.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.redskt.classroom.entity.EduTechnologyBook;
import com.redskt.classroom.entity.RedClassCourse;
import com.redskt.classroom.service.EduTechnologyBookService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2021-02-28
 */
@RestController
@RequestMapping("/home/book/")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class EduTechnologyBookController {

    @Autowired
    private EduTechnologyBookService bookService;

    //查询前8条热门课程，查询前4条名师
    @GetMapping("getBooks")
    public R index() {
        //查询前8条热门课程
        QueryWrapper<EduTechnologyBook> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduTechnologyBook> bookList = bookService.list(wrapper);

        return R.ok().data("bookList",bookList);
    }

}

