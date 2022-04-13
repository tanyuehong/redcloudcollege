package com.redskt.classroom.controller;


import com.qiniu.util.Auth;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.vo.RedAskReplyVo;
import com.redskt.classroom.entity.vo.ReplyCommentVo;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import com.redskt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2020-07-26
 */
@RestController
@RequestMapping("/eduask")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class RedUserAskController {

    @Autowired
    private EduUserAskService userAskService;

    @Autowired
    private RedAskReplyService replyService;

    @Autowired
    private RedAskReplyCommentService commentService;

    @Autowired
    private RedAskAdviseService adviseService;

    @Autowired
    private RedAskWaringService waringService;

    @PostMapping("submit")
    public R registerUser(@RequestBody EduUserAsk userAsk) {
        if (userAskService.saveUserAsk(userAsk)) {
            return R.ok();
        } else  {
            return R.error("问题提交失败，请重新尝试！");
        }
    }

    @PostMapping("submitReply")
    public R registerUser(@RequestBody RedAskReply reply, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (uId.length()>0 && uId.equals(reply.getUid())) {
            if (replyService.save(reply)) {
                RedAskReplyVo rReply = replyService.getUserLasterReply(uId);
                return R.ok().data("reply",rReply);
            } else {
                return R.error("回答存储失败,请重新尝试！");
            }
        }
        return  R.error("登录信息验证失败，请重新尝试！");
    }

    @PostMapping("submitReplyComment")
    public R submitReplyComment(@RequestBody RedAskReplyComment replyComment) {
        replyComment.setGood(0);
        if (commentService.save(replyComment)) {
            ReplyCommentVo myComment = commentService.getUerCommentOne(replyComment.getUid());
            return R.ok().data("comment",myComment);
        } else  {
            return R.error("评论回答失败，请重新尝试！");
        }
    }

    @PostMapping("submitAdvise")
    public R submitAdvise(@RequestBody RedAskAdvise advise) {
        int count = adviseService.updateQustionAdvise(advise.getQid(),advise.getUid(),advise.getType(),advise.getContent());
        if (count >0) {
            return R.ok();
        } else {
            adviseService.save(advise);
            return R.ok();
        }
    }

    @PostMapping("submitWaring")
    public R submitAdvise(@RequestBody RedAskWaring uwaring) {
        int count = waringService.updateContentWarling(uwaring.getWid(),uwaring.getUid(),uwaring.getType(),uwaring.getContent());
        if (count>0) {
            return R.ok().data("tips","ok");
        } else  {
            waringService.save(uwaring);
            return R.ok().data("tips","ok");
        }
    }

    @PostMapping("uploadqiniutoken")
    public  R getUploadImageToken(){
        String accessKey = "RG58GEA8RMtVLfDyxGpSx-82lMwn3bdgKsJxGtap";
        String secretKey = "qUgiA7mJJ8cu0V8wi1F0rDtGK2oD5fMj2i6I4QTF";
        String bucket = "redskt";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        return R.ok().data("token",upToken);
    }
}

