package com.redskt.classroom.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.RedClassUser;
import com.redskt.classroom.entity.RedQustionGood;
import com.redskt.classroom.entity.RedReplyGood;
import com.redskt.classroom.entity.RedUserFocus;
import com.redskt.classroom.entity.vo.RedClassBlogDetailVo;
import com.redskt.classroom.entity.vo.RedClassRegisterVo;
import com.redskt.classroom.service.RedBlogDetailService;
import com.redskt.classroom.service.RedMessageService;
import com.redskt.classroom.service.RedUserFocusService;
import com.redskt.classroom.service.RedUserService;
import com.redskt.commonutils.R;
import com.redskt.security.TokenManager;
import org.apache.commons.lang3.ArrayUtils;
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

    @GetMapping("getUserArticleList/{uId}")
    public R getUserArticleList(@PathVariable String uId) {
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
        return R.ok().data("articleList",postList);
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

    //根据token获取用户信息
    @GetMapping("getShowUserInfo/{uId}")
    public R getShowUserInfo(@PathVariable String uId) {
        //1 根据订单号查询订单信息
        QueryWrapper<RedClassUser> wrapper = new QueryWrapper<>();
        wrapper.select("id","username", "nickname","sex","age","sign","avatar","position","perpage","company","perintroduction");
        wrapper.eq("id",uId);

        RedClassUser eduUser = userService.getOne(wrapper);
        if (eduUser != null && eduUser.getSign()==null) {
            eduUser.setSign("这位同学很懒，木有签名的说～");
        }
        return R.ok().data("userInfo",eduUser);
    }
}

