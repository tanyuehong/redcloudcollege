package com.redskt.classroom.controller;

import com.redskt.classroom.entity.RedClassBanner;
import com.redskt.classroom.service.RedBannerService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-14
 */
@RestController
@RequestMapping("/home/banner")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class RedBannerController {
    @Autowired
    private RedBannerService bannerService;

    //查询所有banner
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<RedClassBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }
}

