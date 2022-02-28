package com.redskt.classroom.controller;


import com.qiniu.util.Auth;
import com.redskt.classroom.entity.EduUserAsk;
import com.redskt.classroom.entity.RedAskReply;
import com.redskt.classroom.entity.RedAskReplyComment;
import com.redskt.classroom.service.EduUserAskService;
import com.redskt.classroom.service.RedAskReplyCommentService;
import com.redskt.classroom.service.RedAskReplyService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class EduUserAskController {

    @Autowired
    private EduUserAskService userAskService;

    @Autowired
    private RedAskReplyService replyService;

    @Autowired
    private RedAskReplyCommentService commentService;

    @PostMapping("submit")
    public R registerUser(@RequestBody EduUserAsk userAsk) {
        if (userAskService.saveUserAsk(userAsk)) {
            return R.ok();
        } else  {
            return R.error("问题提交失败，请重新尝试！");
        }
    }

    @PostMapping("submitReply")
    public R registerUser(@RequestBody RedAskReply reply) {
        if (replyService.save(reply)) {
            return R.ok();
        } else  {
            return R.error("回答提交失败，请重新尝试！");
        }
    }

    @PostMapping("submitReplyComment")
    public R registerUser(@RequestBody RedAskReplyComment replyComment) {
        replyComment.setGood(0);
        if (commentService.save(replyComment)) {
            return R.ok();
        } else  {
            return R.error("评论回答失败，请重新尝试！");
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

