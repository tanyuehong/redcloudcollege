package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.admin.vo.RedInterViewEveryDayQuestionVo;
import com.redskt.classroom.entity.admin.vo.RedInterviewQuestionPositionVo;
import com.redskt.classroom.entity.vo.RedCommentVo;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import com.redskt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/interview/admin")
public class RedInterViewAdminController {

    @Autowired
    private RedInterviewQuestionService questionService;

    @Autowired
    private RedInterviewQuestionPositionService questionPositionService;

    @Autowired
    private RedInterviewPositionClassifyService classifyService;

    @Autowired
    private RedInterviewQuestionEverydayService everydayService;

    @Autowired
    private RedInterviewPositionService positionService;

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
        if (uId.length()>0 && this.userService.checkIsAdmin(uId)) {
            if(classifyService.save(classify)) {
                return R.okSucessTips("添加成功");
            }
            return R.error("存储失败,请重试哈~");
        } else {
            return  R.error("没有对应的权限~");
        }
    }

    @PostMapping("submitEveryQuestion")
    public R submitEveryQuestion(@RequestBody RedInterviewQuestionEveryday questionEveryday,HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if(questionEveryday.getQid()==null || questionEveryday.getPid()==null) {
            return  R.errorParam();
        }
        if (uId.length()>0 && this.userService.checkIsAdmin(uId)) {
            questionEveryday.setUid(uId);
            questionEveryday.setType(1);
            if(everydayService.save(questionEveryday)) {
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
        if (uId.length()>0 && this.userService.checkIsAdmin(uId)) {
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

    @GetMapping("getEveryDayQuestionList/{date}")
    public R getEveryDayQuestionList(@PathVariable String date,HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (date.length()>0 && this.userService.checkIsAdmin(uId)) {
            List<RedInterViewEveryDayQuestionVo> everydayList = everydayService.getInterViewEveryQuestionList(date,null);
            return R.ok().data("everydayList",everydayList);
        }
        return R.errorParam();
    }

    @GetMapping("deleteClassify/{cid}")
    public R positionClassifyList(@PathVariable String cid,HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (cid.length()>0 && this.userService.checkIsAdmin(uId)) {
            if (classifyService.removeById(cid)) {
                return R.okSucessTips("删除成功！");
            } else {
                return R.error("操作异常，请重新操作哦~");
            }
        }
        return R.errorParam();
    }

    @GetMapping("deletePosition/{pid}")
    public R deletePosition(@PathVariable String pid,HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (pid.length()>0 && this.userService.checkIsAdmin(uId)) {
            if (positionService.removeById(pid)) {
                return R.okSucessTips("删除成功！");
            } else {
                return R.error("操作异常，请重新操作哦~");
            }
        }
        return R.errorParam();
    }

    @PostMapping("addInterviewPosition")
    public R addInterviewPosition(@RequestBody RedInterviewPosition position, HttpServletRequest request) {
        if(position.getName()!=null && position.getName().length()>0 && position.getImg()!=null && position.getImg().length()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (this.userService.checkIsAdmin(uId)) {
                if(position.getId() != null && position.getId().length()>0) {
                    if(this.positionService.updateById(position)) {
                        return R.okSucessTips("修改职位成功～");
                    }
                } else {
                    if(this.positionService.save(position)) {
                        return R.okSucessTips("添加职位成功～");
                    }
                }
            }
            return R.error("添加职位失败，请重新尝试哈～");
        } else {
            return R.errorParam();
        }
    }
}
