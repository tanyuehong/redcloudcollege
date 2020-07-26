package com.redskt.classroom.controller;


import com.redskt.classroom.entity.EduUserAsk;
import com.redskt.classroom.service.EduUserAskService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2020-07-26
 */
@RestController
@RequestMapping("/eduask")
public class EduUserAskController {

    @Autowired
    private EduUserAskService userAskService;

    @PostMapping("submit")
    public R registerUser(@RequestBody EduUserAsk userAsk) {
        if (userAskService.saveUserAsk(userAsk)) {
            return R.ok();
        } else  {
            return R.error("报错问题信息失败");
        }
    }



}

