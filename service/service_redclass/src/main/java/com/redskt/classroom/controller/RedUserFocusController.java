package com.redskt.classroom.controller;


import com.redskt.classroom.entity.RedUserFocus;
import com.redskt.classroom.service.RedUserFocusService;
import com.redskt.classroom.service.RedUserService;
import com.redskt.commonutils.R;
import com.redskt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
        return R.error("点赞失败，请稍后重试哈！");
    }
}

