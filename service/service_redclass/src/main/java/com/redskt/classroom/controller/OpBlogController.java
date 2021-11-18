package com.redskt.classroom.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.OpBlogDetail;
import com.redskt.classroom.entity.OpBlogType;
import com.redskt.classroom.entity.RedClassCourse;
import com.redskt.classroom.entity.RedClassTeacher;
import com.redskt.classroom.service.OpBlogDetailService;
import com.redskt.classroom.service.OpBlogTypeService;
import com.redskt.classroom.service.RedCourseService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2021-11-17
 */
@RestController
@RequestMapping("/home/pratice")
public class OpBlogController {

    @Autowired
    private OpBlogTypeService typeService;

    @Autowired
    private OpBlogDetailService blogService;

    @GetMapping("index")
    public R index() {
        QueryWrapper<OpBlogType> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<OpBlogType> typeList = typeService.list(wrapper);

        QueryWrapper<OpBlogDetail> blogDetailQueryWrapper = new QueryWrapper<>();
        blogDetailQueryWrapper.orderByDesc("id");
        blogDetailQueryWrapper.last("limit 4");
        List<OpBlogDetail> blogList = blogService.list(blogDetailQueryWrapper);

        return R.ok().data("typeList",typeList).data("blogList",blogList);
    }
}

