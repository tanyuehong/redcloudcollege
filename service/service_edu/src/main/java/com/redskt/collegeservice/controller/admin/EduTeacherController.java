package com.redskt.collegeservice.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redskt.collegeservice.entity.query.TeacherQuery;
import com.redskt.commonutils.R;
import com.redskt.collegeservice.entity.EduTeacher;
import com.redskt.collegeservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2020-04-26
 */
@Slf4j
@Api(description="讲师管理")
@RestController
@RequestMapping("/teacher")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class EduTeacherController {
    // 把service注入
    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    //添加讲师接口的方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //讲师修改功能
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        String myTeacherId =  eduTeacher.getId();
        if (myTeacherId==null || myTeacherId.length()<=0) {
            return R.error().message("teacherId为空");
        }
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //2 逻辑删除讲师的方法
    @ApiOperation(value = "逻辑删除讲师")
    @PostMapping("deleteTeacher")
    public R removeTeacher(@RequestBody String teacherId) {
        JSONObject jsonObject = JSONObject.parseObject(teacherId);
        String myTeacherId =  (String)jsonObject.get("teacherId");
        if (myTeacherId==null || myTeacherId.length()<=0) {
            return R.error().message("teacherId为空");
        }
        if (jsonObject.size()>1) {
            return R.error().message("参数过多");
        }
        boolean flag = teacherService.removeById(myTeacherId);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据讲师id进行查询
    @PostMapping("getTeacher")
    public R getTeacher(@RequestBody String teacherId) {
        JSONObject jsonObject = JSONObject.parseObject(teacherId);
        String myTeacherId =  (String)jsonObject.get("teacherId");
        if (myTeacherId.isEmpty()) {
            return R.error().message("teacherId为空");
        }
        if (jsonObject.size()>1) {
            return R.error().message("参数过多");
        }
        EduTeacher myTeacher = teacherService.getById(myTeacherId);
        return R.ok().data("teacher", myTeacher);
    }

    //4 条件查询带分页的方法
    @PostMapping("pageTeacherCondition")
    public R pageTeacherCondition(@RequestBody  TeacherQuery teacherQuery) {
        //创建page对象
        Integer current = teacherQuery.getCurrent();
        Integer limit = teacherQuery.getLimit();
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        teacherService.page(pageTeacher,wrapper);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }
}

