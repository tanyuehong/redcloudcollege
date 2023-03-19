package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.admin.vo.RedInterviewQuestionPositionVo;
import com.redskt.classroom.entity.vo.*;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import com.redskt.commonutils.RequestParmUtil;
import com.redskt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/interview/admin")
public class RedInterViewAdminController {

    @Autowired
    private RedInterviewPositionService positionService;

    @Autowired
    private RedInterviewQuestionService questionService;

    @Autowired
    private RedInterviewQuestionPositionService questionPositionService;

    @Autowired
    private RedInterviewPositionClassifyService classifyService;

    @Autowired
    private RedInterviewAnswerService answerService;

    @Autowired
    private RedInterviewCommentService commentService;

    @Autowired
    private RedInterviewGoodService goodService;

    @Autowired
    private RedInterviewCollectService collectService;

    @Autowired
    private RedInterviewAnswerCollectService answerCollectService;

    @Autowired
    private RedUserService userService;


    @GetMapping("questionList")
    public R getQuestionList() {
        QueryWrapper<RedInterviewQuestion> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.orderByAsc("gmt_create");
//            PageHelper.startPage(pId, 20);
        List<RedInterviewQuestion> questionList = questionService.list(questionQueryWrapper);
//            PageInfo page = new PageInfo(typeList);
        return R.ok().data("questionList", questionList);
    }

    @GetMapping("positionList")
    public R getInterviewPosition() {
        QueryWrapper<RedInterviewPosition> positionQueryWrapper = new QueryWrapper<>();
        positionQueryWrapper.orderByAsc("gmt_create");

//            PageHelper.startPage(pId, 20);
        List<RedInterviewPosition> positionList = positionService.list(positionQueryWrapper);
//            PageInfo page = new PageInfo(typeList);
        return R.ok().data("positionList", positionList);
    }

    @GetMapping("positionClassifyList/{pId}")
    public R positionClassifyList(@PathVariable String pId) {
        if (pId.length()>0) {
            QueryWrapper<RedInterviewPositionClassify> classifyQueryWrapper = new QueryWrapper<>();
            classifyQueryWrapper.eq("pid",pId);
            classifyQueryWrapper.orderByAsc("sort");

//            PageHelper.startPage(pId, 20);
            List<RedInterviewPositionClassify> classifyList = classifyService.list(classifyQueryWrapper);
//            PageInfo page = new PageInfo(typeList);
            return R.ok().data("positionClassifyList", classifyList);
        }
        return R.errorParam();
    }

    @GetMapping("questionClassifyList/{qId}")
    public R questionClassifyList(@PathVariable String qId) {
        if (qId.length()>0) {
            List<RedInterviewQuestionPositionVo> questionPositionClassifyList = questionPositionService.getQuestionPositionClassifyList(qId);
            return R.ok().data("questionClassifyList", questionPositionClassifyList);
        }
        return R.errorParam();
    }

    @PostMapping("submitClassify")
    public R submitClassify(@RequestBody RedInterviewPositionClassify classify,HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (uId.length()>0 && this.checkIsAdmin(uId)) {
            if(classifyService.save(classify)) {
                return R.okSucessTips("添加成功");
            }
            return R.error("存储失败,请重试哈~");
        } else {
            return  R.error("没有对应的权限~");
        }
    }

    @PostMapping("submitQuestionPosition")
    public R submitQuestionPosition(@RequestBody RedInterviewQuestionPosition questionPosition,HttpServletRequest request) {
        if (questionPosition.getPid()==null ||questionPosition.getPid().length() == 0 ||
                questionPosition.getQid()==null ||questionPosition.getQid().length() == 0) {
            return R.errorParam();
        }
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (uId.length()>0 && this.checkIsAdmin(uId)) {
            QueryWrapper<RedInterviewQuestionPosition> positionQueryWrapper = new QueryWrapper<>();
            positionQueryWrapper.eq("qid",questionPosition.getQid());
            positionQueryWrapper.eq("pid",questionPosition.getPid());
            List<RedInterviewQuestionPosition> questionPositionList = questionPositionService.list(positionQueryWrapper);
            if (questionPositionList.size()>0) {
                return R.error("本题目已经绑定当前职位，请在列表页面编辑修改哈～");
            }
            if(questionPositionService.save(questionPosition)) {
                return R.okSucessTips("添加题目职位信息成功");
            }
            return R.error("存储失败,请重试哈~");
        } else {
            return  R.error("没有对应的权限~");
        }
    }

    @GetMapping("deleteClassify/{cid}")
    public R positionClassifyList(@PathVariable String cid,HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (cid.length()>0 && this.checkIsAdmin(uId)) {
            if (classifyService.removeById(cid)) {
                return R.okSucessTips("删除成功！");
            } else {
                return R.error("操作异常，请重新操作哦~");
            }
        }
        return R.errorParam();
    }

    private boolean checkIsAdmin(String uId) {
        QueryWrapper<RedClassUser> wrapper = new QueryWrapper<>();
        wrapper.select("id","username","authority");
        wrapper.eq("id",uId);
        RedClassUser eduUser = userService.getOne(wrapper);
        if (eduUser!=null && eduUser.getAuthority() == 100) {
            return true;
        }
        return false;
    }
}
