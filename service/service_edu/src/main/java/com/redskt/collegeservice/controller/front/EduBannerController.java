package com.redskt.collegeservice.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redskt.collegeservice.entity.front.EduBanner;
import com.redskt.collegeservice.service.EduBannerService;
import com.redskt.commonutils.R;
import io.swagger.annotations.ApiOperation;
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
public class EduBannerController {
    @Autowired
    private EduBannerService bannerService;

    //查询所有banner
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<EduBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }


    //1 分页查询banner
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<EduBanner> pageBanner = new Page<>(page,limit);
        bannerService.page(pageBanner,null);
        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    //2 添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody EduBanner crmBanner) {
        bannerService.save(crmBanner);
        return R.ok();
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        EduBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody EduBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }

}

