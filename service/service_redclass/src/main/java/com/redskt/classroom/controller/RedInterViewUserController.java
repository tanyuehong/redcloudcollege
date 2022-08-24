
package com.redskt.classroom.controller;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.*;
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
@RequestMapping("/interview")
public class RedInterViewUserController {

    @Autowired
    private RedInterviewAnswerService answerService;

    @Autowired
    private RedInterviewQuestionService questionService;

    @Autowired
    private RedInterviewTagsService tagsService;

    @Autowired
    private RedInterviewCommentService commentService;

    @Autowired
    private RedInterviewCommentReplyService replyService;

    @Autowired
    private RedInterviewAnswerGoodService answerGoodService;

    @Autowired
    private RedInterviewCommentGoodService commentGoodService;

    @Autowired
    private RedInterviewCommentReplyService commentReplyService;


    @PostMapping("submit")
    public R submitQuestion(@RequestBody Map parameterMap, HttpServletRequest request) {
        parameterMap = RequestParmUtil.transToMAP(parameterMap);
        String uid = (String) parameterMap.get("uid");
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (uid.length()>0 && uId.length()>0 && uid.equals(uId))  {
            String title    = (String) parameterMap.get("title");
            String content  = (String) parameterMap.get("content");
            String qustype  = (String) parameterMap.get("qustype");
            List<String> tags = JSON.parseArray((String) parameterMap.get("tagList"),String.class);

            RedInterviewQuestion question= new RedInterviewQuestion();
            question.setUid(uid);
            question.setTitle(title);
            question.setContent(content);
            question.setQustype(qustype);
            if (questionService.save(question)) {
                List<RedInterviewTags> tagsList = new ArrayList<>();
                for (int i=0;i<tags.size();i++) {
                    RedInterviewTags tag = new RedInterviewTags();
                    tag.setQid(question.getId());
                    tag.setTid(tags.get(i));
                    tagsList.add(tag);
                }
                tagsService.saveBatch(tagsList);
                return R.ok();
            } else  {
                return R.error("问题提交失败，请重新尝试！");
            }
        } else {
            return R.error("参数验证失败，请重新尝试");
        }
    }

    @PostMapping("commet")
    public R submitcommet(@RequestBody RedInterviewComment comment, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if(uId.length()>0 && comment.getUid().length()>0 && uId.equals(comment.getUid())) {
            if (commentService.save(comment)) {
                RedCommentVo curComment = commentService.getCommentOne(comment.getId());
                return R.ok().data("comment",curComment);
            } else  {
                return R.error("评论失败，请重新尝试！");
            }
        } else  {
            return R.error("参数验证失败！");
        }
    }

    @PostMapping("reply")
    public R submitReply(@RequestBody RedInterviewCommentReply reply, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if(uId.length()>0 && reply.getUid().length()>0 && uId.equals(reply.getUid())) {
            if (replyService.save(reply)) {
                RedCommentReplyVo curReply = replyService.getCommentReplyOne(reply.getId());
                return R.ok().data("reply",curReply);
            } else  {
                return R.error("回复评论失败，请重新尝试！");
            }
        } else  {
            return R.error("参数验证失败！");
        }
    }

    @PostMapping("answer")
    public R registerUser(@RequestBody RedInterviewAnswer answer, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (uId.length()>0 && uId.equals(answer.getUid())) {
            if (answerService.save(answer)) {
                RedClassAnswerVo answerVo = answerService.getUserLasterReply(answer.getId());
                return R.ok().data("reply","rReply");
            } else {
                return R.error("提交解答失败败,请重新尝试！");
            }
        }
        return  R.error("登录信息验证失败，请重新尝试！");
    }

    @GetMapping("goodAnswer/{aId}")
    public R goodAnswer(@PathVariable String aId, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (aId.length()>0 && uId.length()>0) {
            if(answerService.updateState(aId,9)>0) {
                return R.ok().data("state", 9);
            }
        }
        return R.error("参数异常，请重新尝试哈！");
    }

    @GetMapping("updateAnswerGood/{aId}/{type}")
    public R updateAnswerGood(@PathVariable String aId, @PathVariable int type, HttpServletRequest request) {
        if (aId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                if (type == 1) {
                    int count = answerGoodService.updateAnswerGoodState(uId, aId);
                    if(count <= 0) {
                        RedInterviewAnswerGood good = new RedInterviewAnswerGood();
                        good.setAid(aId);
                        good.setUid(uId);
                        if (!answerGoodService.save(good)) {
                            return R.error("点赞失败，请重新尝试哈！");
                        }
                    }
                    answerService.updateGoodCount(true, aId);
                    return R.ok().data("goodqustion", true);
                } else {
                    QueryWrapper<RedInterviewAnswerGood> answerGoodQueryWrapper = new QueryWrapper<>();
                    answerGoodQueryWrapper.eq("uid", uId);
                    answerGoodQueryWrapper.eq("aid", aId);
                    if(answerGoodService.remove(answerGoodQueryWrapper)) {
                        answerService.updateGoodCount(false, aId);
                        return R.ok().data("goodqustion", true);
                    } else {
                        return R.error("取消点赞失败，请重新尝试哈！");
                    }
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("参数异常，请重新尝试哈！");
    }

    @GetMapping("updateCommentGood/{cId}/{type}")
    public R updateCommentGood(@PathVariable String cId, @PathVariable int type, HttpServletRequest request) {
        if (cId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                if (type == 1) {
                    int count = commentGoodService.updateGoodState(uId, cId,1);
                    if(count <= 0) {
                        RedInterviewCommentGood good = new RedInterviewCommentGood();
                        good.setCid(cId);
                        good.setUid(uId);
                        if (!commentGoodService.save(good)) {
                            return R.error("点赞失败，请重新尝试哈！");
                        }
                    }
                    commentService.updateGoodCount(true, cId);
                    return R.ok().data("goodqustion", true);
                } else if(type == 2) {
                    QueryWrapper<RedInterviewCommentGood> commentGoodQueryWrapper = new QueryWrapper<>();
                    commentGoodQueryWrapper.eq("uid", uId);
                    commentGoodQueryWrapper.eq("cid", cId);
                    if(commentGoodService.remove(commentGoodQueryWrapper)) {
                        commentService.updateGoodCount(false, cId);
                        return R.ok().data("goodqustion", true);
                    } else {
                        return R.error("取消点赞失败，请重新尝试哈！");
                    }
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("参数异常，请重新尝试哈！");
    }
}
