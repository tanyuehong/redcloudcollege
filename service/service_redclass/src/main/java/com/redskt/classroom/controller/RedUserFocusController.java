package com.redskt.classroom.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.RedQustionGood;
import com.redskt.classroom.entity.RedReplyGood;
import com.redskt.classroom.entity.RedUserFocus;
import com.redskt.classroom.service.RedUserFocusService;
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
 * @since 2022-04-05
 */
@RestController
@RequestMapping("/classroom/userfocus")
public class RedUserFocusController {

    @Autowired
    private RedUserFocusService focusService;

    @GetMapping("addUserFocus/{fId}")
    public R addUserFocus(@PathVariable String fId, HttpServletRequest request) {
        if (fId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                int count = focusService.updateUserFocus(uId, fId);
                if (count <= 0) {
                    RedUserFocus focus = new RedUserFocus();
                    focus.setFid(fId);
                    focus.setUid(uId);
                    if (focusService.save(focus)) {
                        return R.ok().data("focus", true);
                    }
                } else {
                    return R.ok().data("focus", true);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("关注失败，请稍后重试哈！");
    }

    @GetMapping("cancleUserFocus/{fId}")
    public R cancleUserFocus(@PathVariable String fId, HttpServletRequest request) {
        if (fId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                QueryWrapper<RedUserFocus> focusWrapper = new QueryWrapper<>();
                focusWrapper.eq("uid", uId);
                focusWrapper.eq("fid", fId);
                if (focusService.remove(focusWrapper)) {
                    return R.ok().data("focus", false);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("取消关注失败，请稍后重试哈！");
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
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("获取关注失败，请稍后重试哈！");
    }
}

