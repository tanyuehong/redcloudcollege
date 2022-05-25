package com.redskt.classroom.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.RedClassUser;
import com.redskt.classroom.entity.RedQustionGood;
import com.redskt.classroom.entity.RedReplyGood;
import com.redskt.classroom.entity.RedUserFocus;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
import com.redskt.classroom.entity.vo.RedClassBlogDetailVo;
import com.redskt.classroom.entity.vo.RedClassRegisterVo;
import com.redskt.classroom.entity.vo.RedClassUserVo;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import com.redskt.security.TokenManager;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2022-04-05
 */
@RestController
@RequestMapping("/home/ucenter")
public class RedUserHomeController {

    @Autowired
    private RedUserFocusService focusService;

    @Autowired
    private RedUserService userService;

    @Autowired
    private RedBlogDetailService blogService;

    @Autowired
    private RedMessageService messageService;

    @Autowired
    private RedAskService userAskService;

    @GetMapping("getShowUserInfo/{uId}/{type}")
    public R getShowUserInfo(@PathVariable String uId,@PathVariable String type) {
        if(uId == null || uId.length() == 0 || type == null || type.length()==0 ) {
            return R.error("请求参数异常");
        }
        RedClassUserVo eduUser = userService.getShowUserInfo(uId);
        if (eduUser != null && eduUser.getSign()==null) {
            eduUser.setSign("这位同学很懒，木有签名的说～");
        }
        List<RedClassBlogDetailVo> postList = new ArrayList<>();
        if(type.equals("blog")) {
            postList = getArticleList(uId);
        } else if (type.equals("collect-blog")) {
            postList = getCollectArticleList(uId);
        } else if (type.equals("collect-ask")) {

            return R.ok().data("userInfo",eduUser).data("dataList",getCollectAskList(uId));
        }
        return R.ok().data("userInfo",eduUser).data("dataList",postList);
    }


    public List<RedClassBlogDetailVo> getArticleList(String uId) {
        List<RedClassBlogDetailVo> postList = messageService.getRedmessageDetailList(8,1,uId);
        for (int i=0;i<postList.size();i++) {
            RedClassBlogDetailVo detail = postList.get(i);
            detail.setCtype(2);
        }

        List<RedClassBlogDetailVo> blogList = blogService.getRedBlogDetailList(8-postList.size(),1,uId);
        for (int i=0;i<blogList.size();i++) {
            RedClassBlogDetailVo detail = blogList.get(i);
            detail.setCtype(1);
            if (detail.getDescrb().length()>150) {
                detail.setDescrb(detail.getDescrb().substring(0,150)+"...");
            }
        }
        postList.addAll(blogList);
        return postList;
    }

    public List<RedClassBlogDetailVo> getCollectArticleList(String uId) {
        List<RedClassBlogDetailVo> blogList = blogService.getCollectDetailList(8,uId);
        for (int i=0;i<blogList.size();i++) {
            RedClassBlogDetailVo detail = blogList.get(i);
            detail.setCtype(1);
            if (detail.getDescrb().length() > 150) {
                detail.setDescrb(detail.getDescrb().substring(0, 150) + "...");
            }
        }
        return blogList;
    }

    public List<RedClassAskQuestionVo> getCollectAskList(String uId) {
        return userAskService.getCollectQustionLists(8,uId);
    }

    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RedClassRegisterVo registerVo) {
        userService.register(registerVo);
        return R.ok();
    }

    @GetMapping("getUserFocus/{fId}")
    public R getUserGoodState(@PathVariable String fId, HttpServletRequest request) {
        if (fId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                QueryWrapper<RedUserFocus> focusWrapper = new QueryWrapper<>();
                focusWrapper.eq("uid", uId);
                focusWrapper.eq("fid", fId);
                if (focusService.list(focusWrapper).size()>0) {
                    return R.ok().data("focus", true);
                } else {
                    return R.ok().data("focus", false);
                }
            }
        }
        return R.ok().data("focus", false);
    }
}

