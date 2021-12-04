package com.redskt.classroom.controller;


import com.alibaba.fastjson.JSONObject;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        wrapper.isNull("parentId");
        wrapper.orderByAsc("bsort");
        wrapper.last("limit 8");
        List<OpBlogType> typeList = typeService.list(wrapper);

        List<OpBlogType> subTypeList = new ArrayList<>();
        if (typeList.size()>0) {
            QueryWrapper<OpBlogType> subWrapper = new QueryWrapper<>();
            subWrapper.eq("parentId",typeList.get(0).getId());
            subTypeList = typeService.list(subWrapper);
        }

        QueryWrapper<OpBlogDetail> blogDetailQueryWrapper = new QueryWrapper<>();
        blogDetailQueryWrapper.select("id","title","type","good","faver","view_count","price","descrb");
        blogDetailQueryWrapper.orderByDesc("id");
        blogDetailQueryWrapper.last("limit 8");

        List<OpBlogDetail> blogList = blogService.list(blogDetailQueryWrapper);

        for (int i=0;i<blogList.size();i++) {
            OpBlogDetail detail = blogList.get(i);
            if (detail.getDescrb().length()>150) {
                detail.setDescrb(detail.getDescrb().substring(0,150)+"...");
            }
        }
        
        return R.ok().data("typeList",typeList).data("subTypeList",subTypeList).data("blogList",blogList);
    }

    @GetMapping("getDetail/{praticeId}")
    public R index(@PathVariable String praticeId) {
        if (praticeId.length()>0) {
            OpBlogDetail detail = blogService.getById(praticeId);
            return R.ok().data("pitem",detail);
        } else {
            return R.error("参数不合法，请验证");
        }
    }
}

