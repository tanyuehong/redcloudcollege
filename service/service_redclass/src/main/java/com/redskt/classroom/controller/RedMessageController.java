package com.redskt.classroom.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.vo.*;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import com.redskt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2022-05-10
 */
@RestController
@RequestMapping("/home/message")
public class RedMessageController {

    @Autowired
    private RedMessageService messageService;

    @Autowired
    private RedMessageGoodService messageGoodService;

    @Autowired
    private RedMessageCommentService commentService;

    @Autowired
    private RedMessageCommentReplyService replyService;

    @Autowired
    private RedMessageCommentGoodService commentGoodService;


    @GetMapping("getMessageList")
    public R getCommentList() {
        QueryWrapper<RedMessage> wrapper = new QueryWrapper<>();
        wrapper.select("id","uid", "title","type","view_count","gmt_create");
        wrapper.orderByAsc("gmt_create");
        wrapper.last("limit 12");
        List<RedMessage> messageList = messageService.list(wrapper);
        return R.ok().data("messageList",messageList);
    }

    @GetMapping("getCommentList/{mId}/{type}")
    public R getCommentList(@PathVariable String mId,@PathVariable int type) {
        if (mId.length()>0) {
            List<RedMessageCommentVo> commentList = commentService.getMessageCommentList(mId,6,6,type);
            return R.ok().data("comments",commentList);
        } else {
            return R.error("参数不合法，请验证");
        }
    }

    @GetMapping("getMessageDetail/{mId}")
    public R index(@PathVariable String mId) {
        if (mId.length()>0) {
            RedMessageDtailVo detail = messageService.getRedMessageDetail(mId);
            QueryWrapper<RedMessage> wrapper = new QueryWrapper<>();
            wrapper.select("id","uid", "title","cover","type","view_count","gmt_create");
            wrapper.eq("uid",detail.getAuthorUid());
            wrapper.orderByAsc("gmt_create");
            wrapper.last("limit 6");
            List<RedMessage> messageList = messageService.list(wrapper);
            return R.ok().data("pitem",detail).data("messageList",messageList);
        } else {
            return R.error("参数不合法，请验证");
        }
    }

    @GetMapping("addGood/{mId}")
    public R addGood(@PathVariable String mId, HttpServletRequest request) {
        if (mId.length()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                int count = messageGoodService.updateGoodState(uId,mId);
                if (count<=0) {
                    RedMessageGood good = new RedMessageGood();
                    good.setMid(mId);
                    good.setUid(uId);
                    if(messageGoodService.save(good)) {
                        messageService.updateGoodCount(true,mId);
                        return R.ok().data("good", true);
                    }
                } else {
                    messageService.updateGoodCount(true,mId);
                    return R.ok().data("good", true);
                }
            } else {
                return R.error("请您登录以后在点赞该文章哈！");
            }
        }
        return R.error("点赞失败，请稍后重试哈！");
    }

    @GetMapping("cancleGood/{mId}")
    public R cancleGood(@PathVariable String mId, HttpServletRequest request) {
        if (mId.length()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                QueryWrapper<RedMessageGood> goodQueryWrapper = new QueryWrapper<>();
                goodQueryWrapper.eq("mid", mId);
                goodQueryWrapper.eq("uid", uId);
                if(messageGoodService.remove(goodQueryWrapper)) {
                    messageService.updateGoodCount(false,mId);
                    return R.ok().data("good",false);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("取消点赞失败，请稍后重试哈！");
    }

    @GetMapping("status/{mid}")
    public R getUserStatus(@PathVariable String mid, HttpServletRequest request) {
        if (mid.length()>0) {
            messageService.updateReadCount(mid);
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                RedUserStateVo status = messageService.getUserStatus(mid,uId);
                return R.ok().data("status",status);
            }
        }
        return R.ok();
    }

    @PostMapping("commet/submit")
    public R submitReplyComment(@RequestBody RedMessageComment comment, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if(uId.length()>0 && comment.getUid().length()>0 && uId.equals(comment.getUid())) {
            if (commentService.save(comment)) {
                RedMessageCommentVo curComment = commentService.getMessageCommentOne(comment.getId());
                return R.ok().data("comment",curComment);
            } else  {
                return R.error("评论失败，请重新尝试！");
            }
        } else  {
            return R.error("参数验证失败！");
        }
    }

    @PostMapping("commet/submitReply")
    public R submitReplyComment(@RequestBody RedMessageCommentReply reply, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if(uId.length()>0 && reply.getUid().length()>0 && uId.equals(reply.getUid())) {
            if (replyService.save(reply)) {
                RedMessageReplyVo curReply = replyService.getMessageCommentReplyOne(reply.getId());
                return R.ok().data("reply",curReply);
            } else  {
                return R.error("评论文章失败，请重新尝试！");
            }
        } else  {
            return R.error("参数验证失败！");
        }
    }

    @GetMapping("addCommentGood/{cId}/{type}")
    public R addCommentGood(@PathVariable String cId,@PathVariable int type, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (uId.length() > 0 && cId.length()>0) {
            if(commentGoodService.updateCommentGoodState(uId,cId, type==1?1:2)<=0) {
                RedMessageCommentGood good = new RedMessageCommentGood();
                good.setUid(uId);
                good.setCid(cId);
                good.setGtype(type==1?1:2);
                commentGoodService.save(good);
            }
            if(type == 1) {
                commentService.addCommentGoodCount(cId);
            } else {
                replyService.addReplyGoodCount(cId);
            }
            return R.ok().data("goodState",1);
        } else {
            return R.error("参数验证失败！");
        }
    }

    @GetMapping("cancleCommentGood/{cId}/{type}")
    public R cancleGood(@PathVariable String cId,@PathVariable int type, HttpServletRequest request) {
        if (cId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                QueryWrapper<RedMessageCommentGood> goodQueryWrapper = new QueryWrapper<>();
                goodQueryWrapper.eq("cid", cId);
                goodQueryWrapper.eq("uid", uId);
                goodQueryWrapper.eq("gtype", type == 1 ? 1:2);
                if (commentGoodService.remove(goodQueryWrapper)) {
                    if(type == 1) {
                        commentService.prepCommentGoodCount(cId);
                    } else {
                        replyService.prepReplyGoodCount(cId);
                    }
                    return R.ok().data("goodqustion", false);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("取消点赞失败，请稍后重试哈！");
    }
}

