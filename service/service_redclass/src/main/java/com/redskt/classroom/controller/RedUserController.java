package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.RedClassUser;
import com.redskt.classroom.service.RedUserService;
import com.redskt.security.TokenManager;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

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
public class RedUserController {
    @Autowired
    private RedUserService userService;

    //根据token获取用户信息
    @GetMapping("getUserInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String username = TokenManager.getMemberIdByJwtToken(request);

        //1 根据订单号查询订单信息
        QueryWrapper<RedClassUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        RedClassUser eduUser = userService.getOne(wrapper);
        if (eduUser != null && eduUser.getSign()==null) {
            eduUser.setSign("这位同学很懒，木有签名的说～");
        }
        return R.ok().data("userInfo",eduUser);
    }

    @PostMapping("uploadUserImage")
    public  R uploadUerImage(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            return R.error("上传失败，请选择文件");
        }

        String fileName = file.getOriginalFilename();
        String filePath = "C:/Users/tanyuehong/Postman/files";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            return R.ok().data("path",filePath);
        } catch (IOException e) {
            return R.error(e.getLocalizedMessage());
        }
    }
}
