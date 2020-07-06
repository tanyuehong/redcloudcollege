package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.RedClassUser;
import com.redskt.classroom.entity.vo.RedClassRegisterVo;
import com.redskt.classroom.service.RedUserService;
import com.redskt.security.TokenManager;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-16
 */
@RestController
@RequestMapping("/ucenter")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class RedUserController<UcenterMemberService> {
    @Autowired
    private RedUserService userService;

    //注册
    @PostMapping("home/register")
    public R registerUser(@RequestBody RedClassRegisterVo registerVo) {
        userService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("getUserInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String username = TokenManager.getMemberIdByJwtToken(request);

        //1 根据订单号查询订单信息
        QueryWrapper<RedClassUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        RedClassUser eduUser = userService.getOne(wrapper);
        return R.ok().data("userInfo",eduUser);
    }
}
