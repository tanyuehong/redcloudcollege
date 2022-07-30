package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.RedCategoryTag;
import com.redskt.classroom.entity.RedInterviewType;
import com.redskt.classroom.entity.vo.RedClassAnswerVo;
import com.redskt.classroom.entity.vo.RedClassReplyVo;
import com.redskt.classroom.entity.vo.RedCommentVo;
import com.redskt.classroom.entity.vo.RedInterviewQuestionVo;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import com.redskt.commonutils.RequestParmUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home/interview")
public class RedInterViewController {

    @Autowired
    private RedInterviewTypeService typeService;

    @Autowired
    private RedInterviewQuestionService questionService;

    @Autowired
    private RedCategoryTagService tagService;

    @Autowired
    private RedInterviewAnswerService answerService;

    @Autowired
    private RedInterviewCommentService commentService;


    @PostMapping("index")
    public R getInterviewIndex(@RequestBody Map parameterMap) {
        parameterMap = RequestParmUtil.transToMAP(parameterMap);

        String sort = (String) parameterMap.get("sort");
        String tag  = (String) parameterMap.get("tag");

        List<RedInterviewQuestionVo> list = questionService.getHomeInterviewQustionList(sort,tag);

        QueryWrapper<RedCategoryTag> twrapper = new QueryWrapper<>();
        twrapper.orderByAsc("sort");
        twrapper.eq("interview",1);
        List<RedCategoryTag> tagList =  tagService.list(twrapper);
        if(tagList.size()>0) {
            RedCategoryTag allTag = new RedCategoryTag();
            allTag.setName("全部");
            allTag.setId("all");
            tagList.add(0,allTag);
        }

        QueryWrapper<RedInterviewType> typeQueryWrapper = new QueryWrapper<>();
        typeQueryWrapper.orderByAsc("sort");
        List<RedInterviewType> typeList = typeService.list(typeQueryWrapper);

        return R.ok().data("list", list).data("tagList", tagList).data("typeList",typeList);
    }

    @GetMapping("getQuestionDetail/{qId}/{type}")
    public R getQuestionDetail(@PathVariable String qId,@PathVariable int type) {
        if(qId.length()>0) {
            RedInterviewQuestionVo qDetail = questionService.getQustionDetail(qId);
            int readCount = qDetail.getReadcount() + 1;
            questionService.updateQuestionReadCount(qDetail.getQId(), readCount);

            List<RedClassReplyVo> replyList = new ArrayList<>();
            if(type == 1) {
                List<RedClassAnswerVo> answerVoList = answerService.getInterviewAnswerList(qId,1);
                return R.ok().data("qdetail", qDetail).data("dataList", answerVoList);
            } else if(type==2) {
                List<RedCommentVo> commentVoList = commentService.getRedCommentList(qId,5,2);
                return R.ok().data("qdetail", qDetail).data("dataList", commentVoList);
            } else {
                return R.ok().data("qdetail", qDetail).data("dataList", replyList);
            }
        } else {
            return R.error("参数错误，请重新尝试");
        }
    }

    @GetMapping("typelist")
    public R getInterviewTypeList() {
        QueryWrapper<RedInterviewType> typeQueryWrapper = new QueryWrapper<>();
        typeQueryWrapper.orderByAsc("sort");
        List<RedInterviewType> typeList = typeService.list(typeQueryWrapper);
        return R.ok().data("typeList",typeList);
    }

    @GetMapping("tagList/{tId}")
    public R getTypeTagList(@PathVariable String tId) {
        if(tId.length()>0) {
            QueryWrapper<RedCategoryTag> typeQueryWrapper = new QueryWrapper<>();
            typeQueryWrapper.orderByAsc("sort");
            List<RedCategoryTag> tagList = typeService.getInterviewTypeTagList(tId);
            return R.ok().data("tagList",tagList);
        } else {
            return R.error("参数错误，请重新尝试");
        }
    }
}
