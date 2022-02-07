package com.redskt.classroom.controller;


import com.qiniu.util.Auth;
import com.redskt.classroom.entity.EduUserAsk;
import com.redskt.classroom.entity.RedAskReply;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
import com.redskt.classroom.service.EduUserAskService;
import com.redskt.classroom.service.RedAskReplyService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("submit")
    public R registerUser(@RequestBody EduUserAsk userAsk) {
        if (userAskService.saveUserAsk(userAsk)) {
            return R.ok();
        } else  {
            return R.error("报错问题信息失败");
        }
    }


    @PostMapping("submitReply")
    public R registerUser(@RequestBody RedAskReply reply) {
        if (replyService.save(reply)) {
            return R.ok();
        } else  {
            return R.error("报错问题信息失败");
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

