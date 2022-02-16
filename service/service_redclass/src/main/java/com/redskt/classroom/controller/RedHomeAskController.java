package com.redskt.classroom.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.vo.RedAskReplyVo;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import com.redskt.commonutils.RequestParmUtil;
import com.redskt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2022-01-21
 */
@RestController
@RequestMapping("/home/eduask")
public class RedHomeAskController {

    @Autowired
    private RedAskTypeService askTypeService;

    @Autowired
    private EduUserAskService userAskService;

    @Autowired
    private RedAskQustionTagService tagService;

    @Autowired
    private RedAskReplyService replyService;

    @Autowired
    private RedQustionGoodService qustionGoodService;

    @Autowired
    private RedReplyGoodService replyGoodService;

    @Autowired
    private RedReplyBadService replyBadService;



    @PostMapping("questionlist")
    public R getHomeQuestionList(@RequestBody Map parameterMap) {
        QueryWrapper<RedAskType> askWarper = new QueryWrapper<>();
        parameterMap = RequestParmUtil.transToMAP(parameterMap);

        String qustionType = (String) parameterMap.get("qtype");
        int type = Integer.parseInt((String) parameterMap.get("type"));

        askWarper.orderByAsc("sort");
        List<RedAskType> askList = askTypeService.list(askWarper);

        if (qustionType==null || qustionType.length()==0) {
            qustionType = askList.get(0).getId();
        }

        List<RedClassAskQuestionVo> list = userAskService.getHomeAskQustionList(type,qustionType);
        QueryWrapper<RedAskQustionTag> tagQueryWrapper = new QueryWrapper<>();
        tagQueryWrapper.eq("asktype",askList.get(0).getId());
        List<RedAskQustionTag> tagList = tagService.list(tagQueryWrapper);
        return R.ok().data("list",list).data("qustionType",askList).data("tagList",tagList);
    }

    @GetMapping("getquestiondetail/{qId}")
    public R getQustionDetil(@PathVariable String qId,HttpServletRequest request) {
        RedClassAskQuestionVo qDetail =  userAskService.getQustionDetail(qId);
        int readCount = qDetail.getReadcount()+1;
        userAskService.updateUserAskReadCount(qDetail.getQId(),readCount);

        List<RedAskReplyVo> replyList = replyService.getHomeAskReplyList(qDetail.getQId());

        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (uId.length()>0) {

        }
        return R.ok().data("qdetail",qDetail).data("replyList",replyList);
    }

    @GetMapping("qGoodState/{qId}")
    public R getGoodState(@PathVariable String qId, HttpServletRequest request) {
        if (qId.length()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                QueryWrapper<RedQustionGood> goodQueryWrapper = new QueryWrapper<>();
                goodQueryWrapper.eq("qid", qId);
                goodQueryWrapper.eq("uid", uId);
                List<RedQustionGood> goodList = qustionGoodService.list(goodQueryWrapper);
                if (goodList.size() > 0) {
                    return R.ok().data("goodqustion", true);
                }
            }
        }
        return R.ok().data("goodqustion",false);
    }

    @GetMapping("addqGood/{qId}")
    public R addGood(@PathVariable String qId, HttpServletRequest request) {
        if (qId.length()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                int count = qustionGoodService.updateQustionGoodState(uId,qId);
                if (count<=0) {
                    RedQustionGood good = new RedQustionGood();
                    good.setQid(qId);
                    good.setUid(uId);
                    if(qustionGoodService.save(good)) {
                        userAskService.updateQustionGoodCount(true,qId);
                        return R.ok().data("goodqustion", true);
                    }
                } else {
                    userAskService.updateQustionGoodCount(true,qId);
                    return R.ok().data("goodqustion", true);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("点赞失败，请稍后重试哈！");
    }

    @GetMapping("cancleqGood/{qId}")
    public R cancleGood(@PathVariable String qId, HttpServletRequest request) {
        if (qId.length()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                QueryWrapper<RedQustionGood> goodQueryWrapper = new QueryWrapper<>();
                goodQueryWrapper.eq("qid", qId);
                goodQueryWrapper.eq("uid", uId);
                if(qustionGoodService.remove(goodQueryWrapper)) {
                    userAskService.updateQustionGoodCount(false,qId);
                    return R.ok().data("goodqustion",false);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("取消点赞失败，请稍后重试哈！");
    }

    @GetMapping("addRelpyGood/{rId}")
    public R addRelpyGood(@PathVariable String rId, HttpServletRequest request) {
        if (rId.length()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                int count = replyGoodService.updateReplyGoodState(uId,rId);
                if (count<=0) {
                    RedReplyGood good = new RedReplyGood();
                    good.setRid(rId);
                    good.setUid(uId);
                    if(replyGoodService.save(good)) {
                        replyService.updateReplyGoodCount(true,rId);
                        return R.ok().data("goodqustion", true);
                    }
                } else {
                    replyService.updateReplyGoodCount(true,rId);
                    return R.ok().data("goodqustion", true);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("添加解决标签失败，请稍后重试哈！");
    }

    @GetMapping("cancleRelpyGood/{rId}")
    public R cancleRelpyGood(@PathVariable String rId, HttpServletRequest request) {
        if (rId.length()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                QueryWrapper<RedReplyGood> goodQueryWrapper = new QueryWrapper<>();
                goodQueryWrapper.eq("rid", rId);
                goodQueryWrapper.eq("uid", uId);
                if(replyGoodService.remove(goodQueryWrapper)) {
                    replyService.updateReplyGoodCount(false,rId);
                    return R.ok().data("goodqustion",false);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("取消解决标签失败，请稍后重试哈！");
    }

    @GetMapping("addRelpyBad/{rId}")
    public R addRelpyBad(@PathVariable String rId, HttpServletRequest request) {
        if (rId.length()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                int count = replyBadService.updateReplyBadState(uId,rId);
                if (count<=0) {
                    RedReplyBad good = new RedReplyBad();
                    good.setRid(rId);
                    good.setUid(uId);
                    if(replyBadService.save(good)) {
                        replyService.updateReplyGoodCount(true,rId);
                        return R.ok().data("goodqustion", true);
                    }
                } else {
                    replyService.updateReplyBadCount(true,rId);
                    return R.ok().data("goodqustion", true);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("添加无用标签失败，请稍后重试哈！");
    }

    @GetMapping("cancleRelpyBad/{rId}")
    public R cancleRelpyBad(@PathVariable String rId, HttpServletRequest request) {
        if (rId.length()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                QueryWrapper<RedReplyBad> goodQueryWrapper = new QueryWrapper<>();
                goodQueryWrapper.eq("rid", rId);
                goodQueryWrapper.eq("uid", uId);
                if(replyBadService.remove(goodQueryWrapper)) {
                    replyService.updateReplyBadCount(false,rId);
                    return R.ok().data("goodqustion",false);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("取消无用标签失败，请稍后重试哈！");
    }

    @PostMapping("getUserGoodState")
    public R getUserGoodState(@RequestBody List<String> rIds,HttpServletRequest request) {
        if(rIds.size()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if(uId.length()>0) {

              List<RedReplyGood> goodList = replyGoodService.getUserReplyGoodState(rIds,uId);

              return R.ok().data("goodList",goodList);

            }
        }
        return R.ok();
    }
}


