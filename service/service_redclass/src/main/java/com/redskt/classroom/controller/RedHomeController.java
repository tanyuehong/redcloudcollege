package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
import com.redskt.classroom.entity.vo.RedClassRegisterVo;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import com.redskt.commonutils.RequestParmUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-16
 */
@RestController
@RequestMapping("/home")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class RedHomeController<UcenterMemberService> {
    @Autowired
    private RedUserService userService;

    @Autowired
    private RedTeacherService teacherService;

    @Autowired
    private RedCourseService courseService;

    @Autowired
    private EduUserAskService userAskService;

    @Autowired
    private RedAskTypeService askTypeService;

    @Autowired
    private RedAskQustionTagService tagService;

    @PostMapping("eduask/questionlist")
    public  R getHomeQuestionList(@RequestBody Map parameterMap) {
        QueryWrapper<RedAskType > askWarper = new QueryWrapper<>();
        parameterMap = RequestParmUtil.transToMAP(parameterMap);

        String qustionType = (String) parameterMap.get("qtype");
        int type = Integer.parseInt((String) parameterMap.get("type"));

        askWarper.orderByAsc("sort");
        List<RedAskType> askList = askTypeService.list(askWarper);

        if (qustionType==null || qustionType.length()==0) {
            qustionType = askList.get(0).getId();
        }

        List<RedClassAskQuestionVo> list = userAskService.getHomeAskQustionList(type,qustionType);

        QueryWrapper<RedAskQustionTag> tagQueryWrapper = new QueryWrapper<>();
        tagQueryWrapper.eq("asktype",askList.get(0).getId());
        List<RedAskQustionTag> tagList = tagService.list(tagQueryWrapper);
        return R.ok().data("list",list).data("qustionType",askList).data("tagList",tagList);
    }

    @GetMapping("eduask/getquestiondetail/{qId}")
    public R getQustionDetil(@PathVariable String qId) {
        RedClassAskQuestionVo qDetail =  userAskService.getQustionDetail(qId);
        return R.ok().data("qdetail",qDetail);
    }

    //注册
    @PostMapping("ucenter/register")
    public R registerUser(@RequestBody RedClassRegisterVo registerVo) {
        userService.register(registerVo);
        return R.ok();
    }


    //1 分页查询讲师的方法
    @PostMapping("teacher/getTeacherList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
        Page<RedClassTeacher> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(pageTeacher);
        //返回分页所有数据
        return R.ok().data(map);
    }

    //2 讲师详情的功能
    @GetMapping("teacher/getTeacherInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        //1 根据讲师id查询讲师基本信息
        RedClassTeacher eduTeacher = teacherService.getById(teacherId);
        //2 根据讲师id查询所讲课程
        QueryWrapper<RedClassCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<RedClassCourse> courseList = courseService.list(wrapper);
        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }
}
