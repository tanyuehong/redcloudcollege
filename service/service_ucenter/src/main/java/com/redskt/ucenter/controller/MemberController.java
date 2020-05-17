package com.redskt.ucenter.controller;


import com.redskt.commonutils.JwtUtils;
import com.redskt.commonutils.R;
import com.redskt.ucenter.entity.Member;
import com.redskt.ucenter.entity.vo.RegisterVo;
import com.redskt.ucenter.service.MemberService;
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
@RequestMapping("/ucenter/member")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class MemberController<UcenterMemberService> {
    @Autowired
    private MemberService memberService;

    //登录
    @PostMapping("login")
    public R loginUser(@RequestBody Member member) {
        //member对象封装手机号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        Member member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }
}

