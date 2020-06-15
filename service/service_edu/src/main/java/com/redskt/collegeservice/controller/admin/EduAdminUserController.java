package com.redskt.collegeservice.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redskt.collegeservice.entity.admin.EduAdminUser;
import com.redskt.collegeservice.service.EduAdminRoleService;
import com.redskt.collegeservice.service.EduAdminUserService;
import com.redskt.commonutils.R;
import com.redskt.commonutils.MD5;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/usermanger/user")
//@CrossOrigin
public class EduAdminUserController {

    @Autowired
    private EduAdminUserService userService;

    @Autowired
    private EduAdminRoleService roleService;

    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    EduAdminUser userQueryVo) {
        Page<EduAdminUser> pageParam = new Page<>(page, limit);
        QueryWrapper<EduAdminUser> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username",userQueryVo.getUsername());
        }

        IPage<EduAdminUser> pageModel = userService.page(pageParam, wrapper);
        return R.ok().data("items", pageModel.getRecords()).data("total", pageModel.getTotal());
    }

    @ApiOperation(value = "新增管理用户")
    @PostMapping("save")
    public R save(@RequestBody EduAdminUser user) {
        user.setPassword(MD5.encrypt(user.getPassword()));
        userService.save(user);
        return R.ok();
    }

    @ApiOperation(value = "修改管理用户")
    @PutMapping("update")
    public R updateById(@RequestBody EduAdminUser user) {
        userService.updateById(user);
        return R.ok();
    }

    @ApiOperation(value = "删除管理用户")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        userService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "根据id列表删除管理用户")
    @DeleteMapping("batchRemove")
    public R batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return R.ok();
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public R toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return R.ok().data(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public R doAssign(@RequestParam String userId,@RequestParam String[] roleId) {
        roleService.saveUserRoleRealtionShip(userId,roleId);
        return R.ok();
    }
}

