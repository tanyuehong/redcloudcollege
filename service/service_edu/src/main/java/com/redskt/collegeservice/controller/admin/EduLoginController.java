package com.redskt.collegeservice.controller.admin;

import com.redskt.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class EduLoginController {
    @PostMapping("login")
    public R login() {
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
