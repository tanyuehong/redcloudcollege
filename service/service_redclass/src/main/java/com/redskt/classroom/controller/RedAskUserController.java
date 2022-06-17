package com.redskt.classroom.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiniu.util.Auth;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.vo.RedAskReplyVo;
import com.redskt.classroom.entity.vo.RedUserAskVo;
import com.redskt.classroom.entity.vo.ReplyCommentVo;
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
public class RedAskUserController {

    @Autowired
    private RedAskService askService;

    @Autowired
    private RedAskReplyService replyService;

    @Autowired
    private RedAskReplyCommentService commentService;

    @Autowired
    private RedAskAdviseService adviseService;

    @Autowired
    private RedAskWaringService waringService;

    @Autowired
    private RedUserService userService;

    @Autowired
    private RedAskTagsService tagsService;

    @PostMapping("submit")
    public R submitQuestion(@RequestBody Map parameterMap) {
        parameterMap = RequestParmUtil.transToMAP(parameterMap);

        String uid      = (String) parameterMap.get("uid");
        String title    = (String) parameterMap.get("title");
        String content  = (String) parameterMap.get("content");
        String qustype  = (String) parameterMap.get("qustype");

        List<String> tags = JSON.parseArray((String) parameterMap.get("tagList"),String.class);

        RedAskQuestion userAsk= new RedAskQuestion();
        userAsk.setUid(uid);
        userAsk.setTitle(title);
        userAsk.setContent(content);
        userAsk.setQustype(qustype);
        if (askService.saveUserAsk(userAsk)) {
            List<RedAskTags> tagsList = new ArrayList<>();
            for (int i=0;i<tags.size();i++) {
                RedAskTags tag = new RedAskTags();
                tag.setQid(userAsk.getId());
                tag.setTid(tags.get(i));
                tagsList.add(tag);
            }
            tagsService.saveBatch(tagsList);
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

    @GetMapping("getUserAskInfo")
    public R getUserAskInfo(HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (uId.length()>0) {
            RedUserAskVo askVo = userService.getAskUserInfo(uId);
            return R.ok().data("askInfo",askVo);
        }
        return R.error("参数异常，请重新尝试哈！");
    }

    @GetMapping("deleteQuestion/{qId}")
    public R deleteQuestion(@PathVariable String qId, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (qId.length()>0 && uId.length()>0) {
            QueryWrapper<RedAskReply> replyWrapper = new QueryWrapper<>();
            replyWrapper.eq("qid", qId);
            List<RedAskReply> replyList = replyService.list(replyWrapper);
            if (replyList.size()>0) {
                QueryWrapper<RedAskQuestion> questionWrapper = new QueryWrapper<>();
                askService.updateQustionState(qId,uId,99);
                return R.ok().data("sucess",false).data("tips","有回答的问题不支持删除，已经提交删除申请,审核成功后删除哈");
            }
            QueryWrapper<RedAskQuestion> questionWrapper = new QueryWrapper<>();
            questionWrapper.eq("uid", uId);
            questionWrapper.eq("id", qId);
            if(askService.remove(questionWrapper)) {
                return R.ok().data("sucess",true);
            } else {
                return R.error("删除回答失败，请重新尝试哈！");
            }
        }
        return R.error("参数异常，请重新尝试哈！");
    }

    @GetMapping("fixQuestion/{qId}")
    public R fixQuestion(@PathVariable String qId, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (qId.length()>0 && uId.length()>0) {
            QueryWrapper<RedAskReply> replyWrapper = new QueryWrapper<>();
            replyWrapper.eq("qid", qId);
            replyWrapper.eq("state", 9);
            List<RedAskReply> replyList = replyService.list(replyWrapper);
            if (replyList.size()<=0) {
                return R.ok().data("sucess",false).data("tips","该问题还没有最佳答案，请选择或者编写最佳回答以后在设置为已解决哈");
            }
            if(askService.updateQustionState(qId,uId,9)>0) {
                return R.ok().data("sucess",true).data("state",9).data("tips","该问题成功设置为已解决哈！");
            } else {
                return R.error("删除回答失败，请重新尝试哈！");
            }
        }
        return R.error("参数异常，请重新尝试哈！");
    }

    @GetMapping("questionGoodReply/{rId}")
    public R questionGoodReply(@PathVariable String rId, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (rId.length()>0 && uId.length()>0) {
            if(replyService.updateReplyState(rId,9)>0) {
                return R.ok().data("state", 9);
            }
        }
        return R.error("参数异常，请重新尝试哈！");

    }

    @GetMapping("deleteQuestionReply/{rId}")
    public R deleteQuestionReply(@PathVariable String rId, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (rId.length()>0 && uId.length()>0) {
            QueryWrapper<RedAskReply> replyWrapper = new QueryWrapper<>();
            replyWrapper.eq("uid", uId);
            replyWrapper.eq("id", rId);
            if(replyService.remove(replyWrapper)) {
                return R.ok().data("rId",rId);
            } else {
                return R.error("删除回答失败，请重新尝试哈！");
            }
        }
        return R.error("参数异常，请重新尝试哈！");
    }

    @GetMapping("deleteReplyComment/{cId}")
    public R deleteReplyComment(@PathVariable String cId, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (cId.length()>0 && uId.length()>0) {
            QueryWrapper<RedAskReplyComment> commentWrapper = new QueryWrapper<>();
            commentWrapper.eq("uid", uId);
            commentWrapper.eq("id", cId);
            if(commentService.remove(commentWrapper)) {
                return R.ok().data("cId",cId);
            } else {
                return R.error("删除回答失败，请重新尝试哈！");
            }
        }
        return R.error("参数异常，请重新尝试哈！");
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

