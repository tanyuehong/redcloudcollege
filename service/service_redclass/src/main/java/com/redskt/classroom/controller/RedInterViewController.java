package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.RedCategoryTag;
import com.redskt.classroom.entity.RedInterviewGood;
import com.redskt.classroom.entity.RedInterviewType;
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

    @Autowired
    private RedInterviewGoodService goodService;


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

    @GetMapping("addGood/{qId}")
    public R addGood(@PathVariable String qId, HttpServletRequest request) {
        if (qId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                int count = goodService.updateGoodState(uId, qId);
                if (count <= 0) {
                    RedInterviewGood good = new RedInterviewGood();
                    good.setQid(qId);
                    good.setUid(uId);
                    if (goodService.save(good)) {
                        questionService.updateGoodCount(true, qId);
                        return R.ok().data("goodqustion", true);
                    }
                } else {
                    questionService.updateGoodCount(true, qId);
                    return R.ok().data("goodqustion", true);
                }
            } else {
                return R.error("请登录以后在点赞哈！");
            }
        }
        return R.error("点赞失败，请稍后重试哈！");
    }

    @GetMapping("cancleGood/{qId}")
    public R cancleGood(@PathVariable String qId, HttpServletRequest request) {
        if (qId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                QueryWrapper<RedInterviewGood> goodQueryWrapper = new QueryWrapper<>();
                goodQueryWrapper.eq("qid", qId);
                goodQueryWrapper.eq("uid", uId);
                if (goodService.remove(goodQueryWrapper)) {
                    questionService.updateGoodCount(false, qId);
                    return R.ok().data("goodqustion", false);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("取消点赞失败，请稍后重试哈！");
    }

    @GetMapping("status/{qid}")
    public R getInterviewUserStatus(@PathVariable String qid, HttpServletRequest request) {
        if (qid.length()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                RedUserStateVo status = questionService.getUserStatus(qid,uId);
                return R.ok().data("status",status);
            }
        }
        RedUserStateVo status = new RedUserStateVo();
        return R.ok().data("status",status);
    }
}
