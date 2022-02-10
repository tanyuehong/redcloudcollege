package com.redskt.classroom.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.RedQustionCollect;
import com.redskt.classroom.entity.RedQustionGood;
import com.redskt.classroom.service.EduUserAskService;
import com.redskt.classroom.service.RedAskReplyService;
import com.redskt.classroom.service.RedQustionCollectService;
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
 * @since 2022-02-09
 */
@RestController
@RequestMapping("/home/eduask")
public class RedQustionCollectController {

    @Autowired
    private RedQustionCollectService collectService;

    @Autowired
    private EduUserAskService userAskService;

    // 问题的收藏逻辑
    @GetMapping("qCollectState/{qId}")
    public R getGoodState(@PathVariable String qId, HttpServletRequest request) {
        if (qId.length()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                QueryWrapper<RedQustionCollect> goodQueryWrapper = new QueryWrapper<>();
                goodQueryWrapper.eq("qid", qId);
                goodQueryWrapper.eq("uid", uId);
                List<RedQustionCollect> goodList = collectService.list(goodQueryWrapper);
                if (goodList.size() > 0) {
                    return R.ok().data("collectState", true);
                }
            }
        }
        return R.ok().data("collectState",false);
    }

    @GetMapping("addqCollect/{qId}")
    public R addGood(@PathVariable String qId, HttpServletRequest request) {
        if (qId.length()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                int count = collectService.updateQustionCollectState(uId,qId);
                if (count<=0) {
                    RedQustionCollect good = new RedQustionCollect();
                    good.setQid(qId);
                    good.setUid(uId);
                    if(collectService.save(good)) {
                        userAskService.updateQustionCollectCount(true,qId);
                        return R.ok().data("collectState", true);
                    }
                } else {
                    userAskService.updateQustionCollectCount(true,qId);
                    return R.ok().data("collectState", true);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("点赞失败，请稍后重试哈！");
    }

    @GetMapping("cancleqCollect/{qId}")
    public R cancleGood(@PathVariable String qId, HttpServletRequest request) {
        if (qId.length()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                QueryWrapper<RedQustionCollect> goodQueryWrapper = new QueryWrapper<>();
                goodQueryWrapper.eq("qid", qId);
                goodQueryWrapper.eq("uid", uId);
                if(collectService.remove(goodQueryWrapper)) {
                    userAskService.updateQustionCollectCount(false,qId);
                    return R.ok().data("collectState",false);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("取消点赞失败，请稍后重试哈！");
    }

}

