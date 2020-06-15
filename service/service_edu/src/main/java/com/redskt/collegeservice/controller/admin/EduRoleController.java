package com.redskt.collegeservice.controller.admin;


import com.redskt.collegeservice.entity.admin.EduRole;
import com.redskt.commonutils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redskt.collegeservice.service.EduAdminRoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/rolemanger/role")
//@CrossOrigin
public class EduRoleController {

    @Autowired
    private EduAdminRoleService roleService;

    @ApiOperation(value = "获取角色分页列表")
    @GetMapping("{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            EduRole role) {
        Page<EduRole> pageParam = new Page<>(page, limit);
        QueryWrapper<EduRole> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(role.getRoleName())) {
            wrapper.like("role_name",role.getRoleName());
        }
        roleService.page(pageParam,wrapper);
        return R.ok().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        EduRole role = roleService.getById(id);
        return R.ok().data("item", role);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public R save(@RequestBody EduRole role) {
        roleService.save(role);
        return R.ok();
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("update")
    public R updateById(@RequestBody EduRole role) {
        roleService.updateById(role);
        return R.ok();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        roleService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "根据id列表删除角色")
    @DeleteMapping("batchRemove")
    public R batchRemove(@RequestBody List<String> idList) {
        roleService.removeByIds(idList);
        return R.ok();
    }
}

