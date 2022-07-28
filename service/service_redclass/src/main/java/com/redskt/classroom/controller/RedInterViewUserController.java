
package com.redskt.classroom.controller;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.vo.RedCommentReplyVo;
import com.redskt.classroom.entity.vo.RedCommentVo;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import com.redskt.commonutils.RequestParmUtil;
import com.redskt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/interview")
public class RedInterViewUserController {

    @Autowired
    private RedInterviewTypeService typeService;

    @Autowired
    private RedInterviewQuestionService questionService;

    @Autowired
    private RedInterviewTagsService tagsService;

    @Autowired
    private RedInterviewCommentService commentService;

    @Autowired
    private RedInterviewCommentReplyService replyService;


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

    @PostMapping("commet/reply")
    public R submitReplyComment(@RequestBody RedInterviewCommentReply reply, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if(uId.length()>0 && reply.getUid().length()>0 && uId.equals(reply.getUid())) {
            if (replyService.save(reply)) {
                RedCommentReplyVo curReply = replyService.getBlogCommentReplyOne(reply.getId());
                return R.ok().data("reply",curReply);
            } else  {
                return R.error("评论文章失败，请重新尝试！");
            }
        } else  {
            return R.error("参数验证失败！");
        }
    }
}
