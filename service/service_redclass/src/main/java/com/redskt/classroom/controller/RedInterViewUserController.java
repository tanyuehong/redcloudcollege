
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
    private RedInterviewQuestionTagsService tagsService;

    @Autowired
    private RedInterviewCommentService commentService;

    @Autowired
    private RedInterviewCommentReplyService replyService;

    @Autowired
    private RedInterviewAnswerGoodService answerGoodService;

    @Autowired
    private RedInterviewCommentGoodService commentGoodService;

    @Autowired
    private RedInterviewCommentReplyGoodService replyGoodService;

    @Autowired
    private RedInterviewCommentReplyService commentReplyService;

    @Autowired
    private RedInterviewQuestionMeetService meetService;

    @Autowired
    private RedInterviewQuestionCompanyService companyService;

    @Autowired
    private RedInterviewQuestionMeetCompanyService meetCompanyService;

    @Autowired
    private RedInterviewQuestionMeetPositionService questionMeetPositionService;

    @Autowired
    private RedInterviewMeetPositionService meetPositionService;

    @Autowired
    private RedInterviewQuestionPositionService positionService;


    @PostMapping("submit")
    public R submitQuestion(@RequestBody Map parameterMap, HttpServletRequest request) {
        parameterMap = RequestParmUtil.transToMAP(parameterMap);
        String uid = (String) parameterMap.get("uid");
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (uid.length()>0 && uId.length()>0 && uid.equals(uId))  {
            String title      = (String) parameterMap.get("title");
            String content    = (String) parameterMap.get("content");
            String positionId = (String) parameterMap.get("position");
            String classifyId = (String) parameterMap.get("classify");
            List<String> tags = JSON.parseArray((String) parameterMap.get("tagList"),String.class);

            RedInterviewQuestion question= new RedInterviewQuestion();
            question.setUid(uid);
            question.setTitle(title);
            question.setContent(content);
            question.setType("简答题");
            question.setDeep("中等");
            if (questionService.save(question)) {
                RedInterviewQuestionPosition questionPosition = new RedInterviewQuestionPosition();
                questionPosition.setPid(positionId);
                questionPosition.setQid(question.getId());
                if(classifyId!=null && classifyId.length()>0) {
                    questionPosition.setSid(classifyId);
                }
                positionService.save(questionPosition);
                List<RedInterviewQuestionTags> tagsList = new ArrayList<>();
                for (int i=0;i<tags.size();i++) {
                    RedInterviewQuestionTags tag = new RedInterviewQuestionTags();
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
            return R.errorParam();
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
            return R.errorParam();
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
            return R.errorParam();
        }
    }

    @PostMapping("answer")
    public R submitAnswer(@RequestBody RedInterviewAnswer answer, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (uId.length()>0 && uId.equals(answer.getUid())) {
            if (answerService.save(answer)) {
                RedClassAnswerVo answerVo = answerService.getUserLasterReply(answer.getId());
                return R.ok().data("reply",answerVo);
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
        return R.errorParam();
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
        return R.errorParam();
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
        return R.errorParam();
    }

    @GetMapping("deleteComment/{cId}/{type}")
    public R deleteComment(@PathVariable String cId,@PathVariable int type, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (cId.length()>0 && uId.length()>0 ) {
            if(type == 1) {
                QueryWrapper<RedInterviewComment> commentQueryWrapper = new QueryWrapper<>();
                commentQueryWrapper.eq("uid", uId);
                commentQueryWrapper.eq("id", cId);
                if (commentService.remove(commentQueryWrapper)) {
                    return R.ok().data("id", cId);
                } else {
                    return R.error("删除回答失败，请重新尝试哈！");
                }
            } else if(type == 2) {
                QueryWrapper<RedInterviewCommentReply> replyQueryWrapper = new QueryWrapper<>();
                replyQueryWrapper.eq("uid", uId);
                replyQueryWrapper.eq("id", cId);
                if (commentReplyService.remove(replyQueryWrapper)) {
                    return R.ok().data("id", cId);
                } else {
                    return R.error("删除评论回复失败，请重新尝试哈！");
                }
            }
        }
        return R.errorParam();
    }


    @GetMapping("updateCommentReplyGood/{cId}/{type}")
    public R updateCommentReplyGood(@PathVariable String cId, @PathVariable int type, HttpServletRequest request) {
        if (cId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                if (type == 1) {
                    int count = replyGoodService.updateGoodState(uId, cId,1);
                    if(count <= 0) {
                        RedInterviewCommentReplyGood good = new RedInterviewCommentReplyGood();
                        good.setCid(cId);
                        good.setUid(uId);
                        if (!replyGoodService.save(good)) {
                            return R.error("点赞失败，请重新尝试哈！");
                        }
                    }
                    commentReplyService.updateGoodCount(true, cId);
                    return R.ok().data("goodqustion", true);
                } else if(type == 2) {
                    QueryWrapper<RedInterviewCommentReplyGood> replyGoodQueryWrapper = new QueryWrapper<>();
                    replyGoodQueryWrapper.eq("uid", uId);
                    replyGoodQueryWrapper.eq("cid", cId);
                    if(replyGoodService.remove(replyGoodQueryWrapper)) {
                        commentReplyService.updateGoodCount(false, cId);
                        return R.ok().data("goodqustion", true);
                    } else {
                        return R.error("取消点赞失败，请重新尝试哈！");
                    }
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.errorParam();
    }

    @GetMapping("updateMeetType/{qId}/{type}")
    public R updateMeetType(@PathVariable String qId, @PathVariable int type, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (qId.length()>0 && uId.length()>0) {
            RedInterviewQuestionMeet meet = new RedInterviewQuestionMeet();
            meet.setQid(qId);
            meet.setUid(uId);
            if(meetService.save(meet)) {
                questionService.updateMeetType(qId,type);
                return R.okSucessTips("真诚感谢您的反馈～～");
            } else {
                return R.error("操作失败，请重新尝试");
            }
        }
        return R.errorParam();
    }

    @GetMapping("commitMeetCompany/{qId}/{cId}")
    public R commitMeetCompany(@PathVariable String qId, @PathVariable String cId, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (qId.length()>0 && uId.length()>0) {
            RedInterviewQuestionMeetCompany meetCompany = new RedInterviewQuestionMeetCompany();
            meetCompany.setQid(qId);
            meetCompany.setUid(uId);
            meetCompany.setCid(cId);
            if(meetCompanyService.save(meetCompany)) {
                return R.okSucessTips("真诚感谢您的面试公司反馈～～");
            } else {
                return R.error("操作失败，请重新尝试");
            }
        }
        return R.errorParam();
    }

    @PostMapping("addAndSubmitCompany")
    public R addAndSubmitCompany(@RequestBody Map paramMap, HttpServletRequest request) {
        paramMap = RequestParmUtil.transToMAP(paramMap);
        String title = (String) paramMap.get("title");
        String qId = (String) paramMap.get("qId");
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (title.length()>0 && uId.length()>0) {
            RedInterviewQuestionCompany company = new RedInterviewQuestionCompany();
            company.setTitle(title);
            company.setSort(0);
            if (companyService.save(company)) {
                RedInterviewQuestionMeetCompany meetCompany = new RedInterviewQuestionMeetCompany();
                meetCompany.setQid(qId);
                meetCompany.setUid(uId);
                meetCompany.setCid(company.getId());
                if(meetCompanyService.save(meetCompany)) {
                    return R.okSucessTips("真诚感谢您的面试公司反馈～～");
                }
            }
            return R.error("操作失败，请重新尝试");
        }
        return R.errorParam();
    }

    @PostMapping("addAndSubmitPosition")
    public R addAndSubmitPosition(@RequestBody Map paramMap, HttpServletRequest request) {
        paramMap = RequestParmUtil.transToMAP(paramMap);
        String title = (String) paramMap.get("title");
        String qId = (String) paramMap.get("qId");
        String uId = TokenManager.getMemberIdByJwtToken(request);
        RedInterviewMeetPosition position = new RedInterviewMeetPosition();
        position.setTitle(title);
        position.setSort(0);
        if (meetPositionService.save(position)) {
            RedInterviewQuestionMeetPosition meet = new RedInterviewQuestionMeetPosition();
            meet.setQid(qId);
            meet.setUid(uId);
            meet.setPid(position.getId());
            if(questionMeetPositionService.save(meet)) {
                return R.okSucessTips("真诚感谢您的面试职位反馈～～");
            }
            return R.error("操作失败，请重新尝试");
        }
        return R.errorParam();
    }

    @GetMapping("commitMeetPosition/{qId}/{pId}")
    public R commitMeetPosition(@PathVariable String qId, @PathVariable String pId, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (qId.length()>0 && uId.length()>0) {
            RedInterviewQuestionMeetPosition meet = new RedInterviewQuestionMeetPosition();
            meet.setQid(qId);
            meet.setUid(uId);
            meet.setPid(pId);
            if(questionMeetPositionService.save(meet)) {
                return R.okSucessTips("真诚感谢您的面试职位反馈～～");
            } else {
                return R.error("操作失败，请重新尝试");
            }
        }
        return R.errorParam();
    }

    @GetMapping("comPanyList")
    public R getcomPanyList() {
        QueryWrapper<RedInterviewQuestionCompany> companyQueryWrapper = new QueryWrapper<>();
        List<RedInterviewQuestionCompany> companyList = companyService.list(companyQueryWrapper);
        return R.ok().data("companyList",companyList);
    }

    @GetMapping("positionList")
    public R getPositionList() {
        QueryWrapper<RedInterviewQuestionPosition> positionQueryWrapper = new QueryWrapper<>();
        List<RedInterviewQuestionPosition> positionList = positionService.list(positionQueryWrapper);
        return R.ok().data("positionList",positionList);
    }
}
