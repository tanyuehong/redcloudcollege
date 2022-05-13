package com.redskt.classroom.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.RedBlogGood;
import com.redskt.classroom.entity.RedMessage;
import com.redskt.classroom.entity.RedMessageGood;
import com.redskt.classroom.entity.vo.RedMessageDtailVo;
import com.redskt.classroom.entity.vo.RedUserStateVo;
import com.redskt.classroom.service.RedMessageGoodService;
import com.redskt.classroom.service.RedMessageService;
import com.redskt.commonutils.R;
import com.redskt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("getMessageList")
    public R getCommentList() {
        QueryWrapper<RedMessage> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("gmt_create");
        wrapper.last("limit 12");
        List<RedMessage> messageList = messageService.list(wrapper);
        return R.ok().data("messageList",messageList);
    }

    @GetMapping("getMessageDetail/{mId}")
    public R index(@PathVariable String mId) {
        if (mId.length()>0) {
            RedMessageDtailVo detail = messageService.getRedMessageDetail(mId);
            return R.ok().data("pitem",detail);
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
}

