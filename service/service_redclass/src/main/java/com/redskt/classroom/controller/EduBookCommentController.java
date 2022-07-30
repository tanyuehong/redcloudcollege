package com.redskt.classroom.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.EduBookComment;
import com.redskt.classroom.service.EduBookCommentService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2021-03-28
 */
@RestController
@RequestMapping("/home/book/comments")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class EduBookCommentController {
    @Autowired
    private EduBookCommentService commentService;

    @PostMapping("getBookComments")
    public R getBookComments(@RequestBody String jsonString) {
        //查询前8条热门课程
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String bookId = (String)jsonObject.get("bookId");
        int type = (int)jsonObject.get("type");
        QueryWrapper<EduBookComment> wrapper = new QueryWrapper<>();
        wrapper.eq("book_id",bookId);
        if(type == 1) {
            wrapper.orderByDesc("view_count");
        }
        if(type == 2) {
            wrapper.orderByDesc("praise");
        }
        if(type == 3) {
            wrapper.orderByDesc("gmt_create");
        }
        wrapper.last("limit 8");
        List<EduBookComment> bookList = commentService.list(wrapper);
        return R.ok().data("comments",bookList);
    }
}

