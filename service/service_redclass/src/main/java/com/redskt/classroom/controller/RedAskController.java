package com.redskt.classroom.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.vo.RedClassReplyVo;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import com.redskt.commonutils.RequestParmUtil;
import com.redskt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2022-01-21
 */
@RestController
@RequestMapping("/home/eduask")
public class RedAskController {
    @Autowired
    private RedAskTypeService askTypeService;
    @Autowired
    private RedAskService userAskService;
    @Autowired
    private RedCategoryTagService tagService;
    @Autowired
    private RedAskReplyService replyService;
    @Autowired
    private RedQustionGoodService qustionGoodService;
    @Autowired
    private RedReplyGoodService replyGoodService;
    @Autowired
    private RedReplyCommentGoodService replyCGoodService;
    @Autowired
    private RedAskReplyCommentService commentService;
    @Autowired
    private RedisTemplate redisTemplate;  //存储对象


    @GetMapping("questionTypeList")
    public R getQuestionTypeList() {
        QueryWrapper<RedAskType> askWarper = new QueryWrapper<>();
        askWarper.orderByAsc("sort");
        List<RedAskType> typeList = askTypeService.list(askWarper);
        typeList.remove(0);

        String key = RequestParmUtil.generateKeyAndIv();
        String pagekey1 =  RequestParmUtil.getPageKey("submitQustion",key);
        String pagekey = URLEncoder.encode(pagekey1);

        this.redisTemplate.opsForValue().set(pagekey,key,30, TimeUnit.MINUTES);

        return R.ok().data("typeList",typeList).data("pageKey", pagekey);
    }

    @PostMapping("questionlist")
    public R getHomeQuestionList(@RequestBody Map parameterMap) {
        QueryWrapper<RedAskType> askWarper = new QueryWrapper<>();
        parameterMap = RequestParmUtil.transToMAP(parameterMap);

        String type = (String) parameterMap.get("type");
        String sort = (String) parameterMap.get("sort");
        String tag  = (String) parameterMap.get("tag");

        int sortType = 2;
        if(sort == "" || sort == null || sort.equals("latestq")) {  // 代表 默认的逻辑
            sortType = 2;
        } else if(sort.equals("hot")) {
            sortType = 1 ;
        } else if(sort.equals("latesta")) {
            sortType = 3;
        } else if(sort.equals("wait")) {
            sortType = 4;
        } else if(sort.equals("mosta")) {
            sortType = 5;
        } else if(sort.equals("payq")) {
            sortType = 6;
        }

        askWarper.orderByAsc("sort");
        List<RedAskType> askList = askTypeService.list(askWarper);

        if (type.length() == 0 || type.equals("all")) {
            type = null;
        }
        List<RedClassAskQuestionVo> list = userAskService.getHomeAskQustionList(sortType, type,tag);
        QueryWrapper<RedCategoryTag> tagQueryWrapper = new QueryWrapper<>();
        tagQueryWrapper.eq("asktype", askList.get(0).getId());
        List<RedCategoryTag> tagList = tagService.list(tagQueryWrapper);


        List<Map> sortList = new ArrayList<>();

        Map<String, String> latestQMap = new HashMap<>();
        latestQMap.put("name","最新提问");
        latestQMap.put("path","latestq");
        sortList.add(latestQMap);

        Map<String, String> hotMap = new HashMap<>();
        hotMap.put("name","热门排行");
        hotMap.put("path","hot");
        sortList.add(hotMap);
        Map<String, String> mostAMap = new HashMap<>();

        Map<String, String> latestAMap = new HashMap<>();
        latestAMap.put("name","最新回答");
        latestAMap.put("path","latesta");
        sortList.add(latestAMap);

        Map<String, String> waitMap = new HashMap<>();
        waitMap.put("name","等待回答");
        waitMap.put("path","wait");
        sortList.add(waitMap);

        mostAMap.put("name","最多回答");
        mostAMap.put("path","mosta");
        sortList.add(mostAMap);

        Map<String, String> payqMap = new HashMap<>();
        payqMap.put("name","付费问答");
        payqMap.put("path","payq");
        sortList.add(payqMap);

        return R.ok().data("list", list).data("qustionType", askList).data("tagList", tagList).data("sortList",sortList);
    }

    @GetMapping("getquestiondetail/{qId}")
    public R getQustionDetil(@PathVariable String qId) {
        RedClassAskQuestionVo qDetail = userAskService.getQustionDetail(qId);
        int readCount = qDetail.getReadcount() + 1;
        userAskService.updateUserAskReadCount(qDetail.getQId(), readCount);

        String key = RequestParmUtil.generateKeyAndIv();
        String pagekey1 =  RequestParmUtil.getPageKey("submitQustion",key);
        String pagekey = URLEncoder.encode(pagekey1);

        this.redisTemplate.opsForValue().set(pagekey,key,30, TimeUnit.MINUTES);

        List<RedClassReplyVo> replyList = replyService.getHomeAskReplyList(qDetail.getQId(),1);
        return R.ok().data("qdetail", qDetail).data("replyList", replyList).data("pageKey",pagekey);
    }

    @GetMapping("getQustionReplyList/{qId}/{sortType}")
    public R getQustionReplyList(@PathVariable String qId,@PathVariable int sortType) {
        if(qId.length()>0) {
            List<RedClassReplyVo> replyList = replyService.getHomeAskReplyList(qId,sortType);
            return R.ok().data("replyList", replyList);
        }
        return R.error("参数异常");
    }

    @GetMapping("qGoodState/{qId}")
    public R getGoodState(@PathVariable String qId, HttpServletRequest request) {
        if (qId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                QueryWrapper<RedQustionGood> goodQueryWrapper = new QueryWrapper<>();
                goodQueryWrapper.eq("qid", qId);
                goodQueryWrapper.eq("uid", uId);
                List<RedQustionGood> goodList = qustionGoodService.list(goodQueryWrapper);
                if (goodList.size() > 0) {
                    return R.ok().data("goodqustion", true);
                }
            }
        }
        return R.ok().data("goodqustion", false);
    }

    @GetMapping("addqGood/{qId}")
    public R addGood(@PathVariable String qId, HttpServletRequest request) {
        if (qId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                int count = qustionGoodService.updateQustionGoodState(uId, qId);
                if (count <= 0) {
                    RedQustionGood good = new RedQustionGood();
                    good.setQid(qId);
                    good.setUid(uId);
                    if (qustionGoodService.save(good)) {
                        userAskService.updateQustionGoodCount(true, qId);
                        return R.ok().data("goodqustion", true);
                    }
                } else {
                    userAskService.updateQustionGoodCount(true, qId);
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
        if (qId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                QueryWrapper<RedQustionGood> goodQueryWrapper = new QueryWrapper<>();
                goodQueryWrapper.eq("qid", qId);
                goodQueryWrapper.eq("uid", uId);
                if (qustionGoodService.remove(goodQueryWrapper)) {
                    userAskService.updateQustionGoodCount(false, qId);
                    return R.ok().data("goodqustion", false);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("取消点赞失败，请稍后重试哈！");
    }

    @GetMapping("addCGood/{cId}")
    public R addCGood(@PathVariable String cId, HttpServletRequest request) {
        if (cId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                int count = replyCGoodService.updateReplyCommentGoodState(uId, cId);
                if (count <= 0) {
                    RedReplyCommentGood good = new RedReplyCommentGood();
                    good.setCid(cId);
                    good.setUid(uId);
                    if (replyCGoodService.save(good)) {
                        commentService.updateReplyCommentGoodCount(true, cId);
                        return R.ok().data("goodqustion", true);
                    }
                } else {
                    commentService.updateReplyCommentGoodCount(true, cId);
                    return R.ok().data("goodqustion", true);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("点赞失败，请稍后重试哈！");
    }

    @GetMapping("canclecGood/{cId}")
    public R canclecGood(@PathVariable String cId, HttpServletRequest request) {
        if (cId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                QueryWrapper<RedReplyCommentGood> goodQueryWrapper = new QueryWrapper<>();
                goodQueryWrapper.eq("cid", cId);
                goodQueryWrapper.eq("uid", uId);
                if (replyCGoodService.remove(goodQueryWrapper)) {
                    commentService.updateReplyCommentGoodCount(false, cId);
                    return R.ok().data("goodqustion", false);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("取消点赞失败，请稍后重试哈！");
    }

    @GetMapping("updateRelpyState/{rId}/{type}")
    public R cancleRelpyGood(@PathVariable String rId, @PathVariable int type, HttpServletRequest request) {
        if (rId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                if (type == 1 || type == 2) {
                    int count = replyGoodService.updateReplyGoodState(uId, rId, type);
                    if (count <= 0) {
                        RedReplyGood good = new RedReplyGood();
                        good.setRid(rId);
                        good.setUid(uId);
                        good.setType(type);
                        if (replyGoodService.save(good)) {
                            if (type == 1) {
                                replyService.updateReplyGoodCount(true, rId);
                            } else {
                                replyService.updateReplyBadCount(true, rId);
                            }
                            return R.ok().data("goodqustion", true);
                        }
                    } else {
                        if (type == 1) {
                            replyService.updateReplyGoodCount(true, rId);
                        } else {
                            replyService.updateReplyBadCount(true, rId);
                        }
                        return R.ok().data("goodqustion", true);
                    }
                } else if (type == 3 || type == 4) {
                    QueryWrapper<RedReplyGood> goodQueryWrapper = new QueryWrapper<>();
                    goodQueryWrapper.eq("rid", rId);
                    goodQueryWrapper.eq("uid", uId);
                    if (replyGoodService.remove(goodQueryWrapper)) {
                        if (type == 3) {
                            replyService.updateReplyGoodCount(false, rId);
                        } else {
                            replyService.updateReplyBadCount(false, rId);
                        }

                        return R.ok().data("goodqustion", false);
                    }
                } else if (type == 5) {
                    int count = replyGoodService.updateReplyGoodState(uId, rId, 2);
                    if (count > 0) {
                        replyService.updateReplyBadCount(true, rId);
                    }
                    return R.ok().data("goodqustion", true);
                } else if (type == 6) {
                    int count = replyGoodService.updateReplyGoodState(uId, rId, 1);
                    if (count > 0) {
                        replyService.updateReplyGoodCount(true, rId);
                    }
                    return R.ok().data("goodqustion", true);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.ok().data("goodqustion", true);
    }

    @PostMapping("getUserGoodState")
    public R getUserGoodState(@RequestBody List<String> rIds, HttpServletRequest request) {
        if (rIds.size() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                List<RedReplyGood> goodList = replyGoodService.getUserReplyGoodState(rIds, uId);
                return R.ok().data("goodList", goodList);
            }
        }
        return R.ok();
    }
}


