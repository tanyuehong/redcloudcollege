package com.redskt.classroom.controller;

import com.redskt.classroom.entity.RedBlogComment;
import com.redskt.classroom.entity.RedBlogCommentReply;
import com.redskt.classroom.entity.vo.RedBlogCommentReplyVo;
import com.redskt.classroom.entity.vo.RedBlogCommentVo;
import com.redskt.classroom.service.RedBlogCommentReplyService;
import com.redskt.classroom.service.RedBlogCommentService;
import com.redskt.commonutils.R;
import com.redskt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/blogCommet")
public class RedBlogUserController {

    @Autowired
    private RedBlogCommentService commentService;

    @Autowired
    private RedBlogCommentReplyService replyService;

    @PostMapping("submit")
    public R submitReplyComment(@RequestBody RedBlogComment comment, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if(uId.length()>0 && comment.getUid().length()>0 && uId.equals(comment.getUid())) {
            if (commentService.save(comment)) {
                RedBlogCommentVo curComment = commentService.getBlogCommentOne(comment.getId());
                return R.ok().data("comment",comment);
            } else  {
                return R.error("评论文章失败，请重新尝试！");
            }
        } else  {
            return R.error("参数验证失败！");
        }
    }

    @PostMapping("submitReply")
    public R submitReplyComment(@RequestBody RedBlogCommentReply reply, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if(uId.length()>0 && reply.getUid().length()>0 && uId.equals(reply.getUid())) {
            if (replyService.save(reply)) {
                RedBlogCommentReplyVo curReply = replyService.getBlogCommentReplyOne(reply.getId());
                return R.ok().data("reply",curReply);
            } else  {
                return R.error("评论文章失败，请重新尝试！");
            }
        } else  {
            return R.error("参数验证失败！");
        }
    }
}
