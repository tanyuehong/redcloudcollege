package com.redskt.classroom.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pig4cloud.captcha.GifCaptcha;
import com.pig4cloud.captcha.base.Captcha;
import com.redskt.classroom.entity.RedReplyGood;
import com.redskt.classroom.entity.RedUserFocus;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
import com.redskt.classroom.entity.vo.RedClassBlogDetailVo;
import com.redskt.classroom.entity.vo.RedClassRegisterVo;
import com.redskt.classroom.entity.vo.RedClassUserVo;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import com.redskt.commonutils.RequestParmUtil;
import com.redskt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate redisTemplate;  //存储对象


    @GetMapping("/captcha")
    public void defaultCaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/png");

        // 三个参数分别为宽、高、位数

        GifCaptcha captcha = new GifCaptcha(130, 35, 6);

        // 设置类型 数字和字母混合
        captcha.setCharType(Captcha.TYPE_DEFAULT);

        //设置字体
        captcha.setCharType(Captcha.FONT_8);
        String imageKey1 = httpServletRequest.getParameter("pageKey");
        String imageKey = URLEncoder.encode(imageKey1);
        String key = (String) this.redisTemplate.opsForValue().get(imageKey);
        String decodePageKey = RequestParmUtil.desEncrypt(imageKey1,key);
        decodePageKey = decodePageKey.substring(0,decodePageKey.length()-3);

        this.redisTemplate.opsForValue().set(decodePageKey,captcha.text().toLowerCase(),60, TimeUnit.SECONDS);

        // 输出图片流
        captcha.out(httpServletResponse.getOutputStream());
    }

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
        } else if (type.equals("focus-mine")) {
            return R.ok().data("userInfo",eduUser).data("dataList",getFocusUserList(uId,1));
        } else if (type.equals("focus-fans")) {
            return R.ok().data("userInfo",eduUser).data("dataList",getFocusUserList(uId,2));
        } else if (type.equals("good-blog")) {
            postList = getGoodArticleList(uId);
        } else if (type.equals("good-ask")) {
            return R.ok().data("userInfo",eduUser).data("dataList", getGoodAskList(uId));
        } else if (type.equals("draft")) {
            return R.ok().data("userInfo",eduUser).data("dataList", getGoodAskList(uId));
        }
        return R.ok().data("userInfo",eduUser).data("dataList",postList);
    }

    public List<RedClassUserVo> getFocusUserList(String uid,int type) {
        if(type == 1) {
            return userService.getFocusUserList(uid);
        } else {
            return userService.getFansUserList(uid);
        }
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

    public List<RedClassBlogDetailVo> getGoodArticleList(String uId) {
        List<RedClassBlogDetailVo> blogList = blogService.getGoodDetailList(8,uId);
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

    public List<RedClassAskQuestionVo> getGoodAskList(String uId) {
        return userAskService.getGoodQustionLists(8,uId);
    }

    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RedClassRegisterVo registerVo) {
        if(!registerVo.checkParameter()) {
            String key = (String) redisTemplate.opsForValue().get(registerVo.getPageKey());
            String decodePageKey =  URLDecoder.decode(registerVo.getPageKey());

            String  pageKey =  RequestParmUtil.desEncrypt(decodePageKey,key);
            pageKey = pageKey.substring(0,pageKey.length()-3);

            String verCode = (String) redisTemplate.opsForValue().get(pageKey);
            if(verCode == null) {
                return R.ok().data("result",3).data("messageTips","验证码已经过期，请点击图片刷新");
            }
            if(!verCode.toLowerCase().equals(registerVo.getVerCode().toLowerCase())) {
                return R.ok().data("result",2).data("messageTips","验证码输入错误，请重新输入");
            }

            int result = userService.register(registerVo);
            if(result == 1) {
                return R.ok().data("result",result);
            } else if(result == 4) {
                return R.ok().data("result",result).data("messageTips","参数验证失败");
            } else if(result == 5) {
                return R.ok().data("result",result).data("messageTips","该手机号已经被注册了哦");
            } else {
                return R.okSucessTips("登录异常，请重新尝试").data("result",6);
            }
        }
        return R.ok().data("result",4).data("messageTips","参数验证失败");
    }

    @GetMapping("getRegisterPage")
    public R getRegisterPage() {
        String key = RequestParmUtil.generateKeyAndIv();
        String pagekey1 =  RequestParmUtil.getPageKey("register",key);
        String pagekey = URLEncoder.encode(pagekey1);
        this.redisTemplate.opsForValue().set(pagekey,key,30, TimeUnit.MINUTES);
        return R.ok().data("pagekey", pagekey);
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

    @PostMapping("getUserFocusState")
    public R getUserFocusState(@RequestBody List<String> fIds, HttpServletRequest request) {
        if (fIds.size() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                List<RedReplyGood> goodList = focusService.getUserFocusState(fIds, uId);
                return R.ok().data("focusList", goodList);
            }
        }
        return R.ok();
    }
}

