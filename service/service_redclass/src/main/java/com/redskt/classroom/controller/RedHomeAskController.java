package com.redskt.classroom.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.RedAskQustionTag;
import com.redskt.classroom.entity.RedAskReply;
import com.redskt.classroom.entity.RedAskType;
import com.redskt.classroom.entity.vo.RedAskReplyVo;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
import com.redskt.classroom.service.EduUserAskService;
import com.redskt.classroom.service.RedAskQustionTagService;
import com.redskt.classroom.service.RedAskReplyService;
import com.redskt.classroom.service.RedAskTypeService;
import com.redskt.commonutils.R;
import com.redskt.commonutils.RequestParmUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2022-01-21
 */
@RestController
@RequestMapping("/home")
public class RedHomeAskController {

    @Autowired
    private RedAskTypeService askTypeService;

    @Autowired
    private EduUserAskService userAskService;

    @Autowired
    private RedAskQustionTagService tagService;

    @Autowired
    private RedAskReplyService replyService;


    @PostMapping("eduask/questionlist")
    public R getHomeQuestionList(@RequestBody Map parameterMap) {
        QueryWrapper<RedAskType> askWarper = new QueryWrapper<>();
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
        int readCount = qDetail.getReadcount()+1;
        userAskService.updateUserAskReadCount(qDetail.getQId(),readCount);

        List<RedAskReplyVo> replyList = replyService.getHomeAskReplyList(qDetail.getQId());
        return R.ok().data("qdetail",qDetail).data("replyList",replyList);
    }


}


