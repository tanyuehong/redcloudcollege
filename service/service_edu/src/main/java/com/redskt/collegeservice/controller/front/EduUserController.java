package com.redskt.collegeservice.controller.front;

import com.redskt.collegeservice.entity.front.EduUser;
import com.redskt.collegeservice.entity.front.vo.RegisterVo;
import com.redskt.collegeservice.service.EduUserService;
import com.redskt.commonutils.JwtUtils;
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
@RequestMapping("/ucenter/user")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class EduUserController<UcenterMemberService> {
    @Autowired
    private EduUserService userService;

    //登录
    @PostMapping("login")
    public R loginUser(@RequestBody EduUser eduUser) {
        //member对象封装手机号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token = userService.login(eduUser);
        return R.ok().data("token",token);
    }

    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        userService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("getUserInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        EduUser eduUser = userService.getById(memberId);
        return R.ok().data("userInfo",eduUser);
    }
}
