package com.redskt.classroom.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.RedMessage;
import com.redskt.classroom.entity.vo.RedMessageDtailVo;
import com.redskt.classroom.service.RedMessageService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2022-05-10
 */
@RestController
@RequestMapping("/home/message")
public class RedMessageController {

    @Autowired
    private RedMessageService messageService;

    @GetMapping("getMessageList")
    public R getCommentList() {
        QueryWrapper<RedMessage> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("gmt_create");
        wrapper.last("limit 12");
        List<RedMessage> messageList = messageService.list(wrapper);
        return R.ok().data("messageList",messageList);
    }

    @GetMapping("getMessageDetail/{mId}")
    public R index(@PathVariable String mId) {
        if (mId.length()>0) {
            RedMessageDtailVo detail = messageService.getRedMessageDetail(mId);
            messageService.updateReadCount(mId);
            return R.ok().data("pitem",detail);
        } else {
            return R.error("参数不合法，请验证");
        }
    }
}

