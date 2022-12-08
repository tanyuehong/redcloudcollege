package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.vo.*;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/home/interview")
public class RedInterViewController {

    @Autowired
    private RedInterviewTypeService typeService;

    @Autowired
    private RedInterviewQuestionService questionService;

    @Autowired
    private RedCategoryTagService tagService;

    @Autowired
    private RedInterviewAnswerService answerService;

    @Autowired
    private RedInterviewCommentService commentService;

    @Autowired
    private RedInterviewGoodService goodService;

    @Autowired
    private RedInterviewCollectService collectService;

    @Autowired
    private RedInterviewAnswerCollectService answerCollectService;

    @Autowired
    private RedisTemplate redisTemplate;  //存储对象


    @PostMapping("index")
    public R getInterviewIndex(@RequestBody Map parameterMap) {
        parameterMap = RequestParmUtil.transToMAP(parameterMap);

        String sort = (String) parameterMap.get("sort");
        String tag  = (String) parameterMap.get("tag");

//        PageHelper.startPage(1, 20  );
        List<RedInterviewQuestionVo> list = questionService.getHomeInterviewQustionList(sort,tag);
        PageInfo page = new PageInfo(list);

        QueryWrapper<RedCategoryTag> twrapper = new QueryWrapper<>();
        twrapper.orderByAsc("sort");
        twrapper.eq("interview",1);
        List<RedCategoryTag> tagList =  tagService.list(twrapper);
        if(tagList.size()>0) {
            RedCategoryTag allTag = new RedCategoryTag();
            allTag.setName("全部");
            allTag.setPath("all");
            allTag.setId("all");
            tagList.add(0,allTag);
        }

        QueryWrapper<RedInterviewType> typeQueryWrapper = new QueryWrapper<>();
        typeQueryWrapper.orderByAsc("sort");
        List<RedInterviewType> typeList = typeService.list(typeQueryWrapper);

        return R.ok().data("list", list).data("tagList", tagList).data("typeList",typeList);
    }

    @PostMapping("getQuestionDetail")
    public R getQuestionDetail(@RequestBody Map parameterMap) {
        parameterMap = RequestParmUtil.transToMAP(parameterMap);
        String typeString= (String) parameterMap.get("type");
        Integer type = Integer.parseInt(typeString);

        String qId = (String) parameterMap.get("qId");
        String token = (String) parameterMap.get("token");
        String uId = TokenManager.getUserFromToken(token);

        String key = RequestParmUtil.generateKeyAndIv();
        String pagekey1 =  RequestParmUtil.getPageKey("submitQustion",key);
        String pagekey = URLEncoder.encode(pagekey1);

        this.redisTemplate.opsForValue().set(pagekey,key,30, TimeUnit.MINUTES);

        if(qId.length()>0) {
            RedInterviewQuestionVo qDetail = questionService.getQustionDetail(qId);
            int readCount = qDetail.getReadcount() + 1;
            questionService.updateQuestionReadCount(qDetail.getQId(), readCount);

            List<RedClassReplyVo> replyList = new ArrayList<>();
            if(type == 1) {
                List<RedClassAnswerVo> answerVoList = answerService.getInterviewAnswerList(qId,uId,1);
                return R.ok().data("qdetail", qDetail).data("dataList", answerVoList).data("pageKey",pagekey);
            } else if(type==2) {
                List<RedCommentVo> commentVoList = commentService.getRedCommentList(qId,uId,5,2);
                return R.ok().data("qdetail", qDetail).data("dataList", commentVoList).data("pageKey",pagekey);
            } else {
                return R.ok().data("qdetail", qDetail).data("dataList", replyList).data("pageKey",pagekey);
            }
        } else {
            return R.error("参数错误，请重新尝试");
        }
    }

    @GetMapping("typelist")
    public R getInterviewTypeList() {
        QueryWrapper<RedInterviewType> typeQueryWrapper = new QueryWrapper<>();
        typeQueryWrapper.orderByAsc("sort");
        List<RedInterviewType> typeList = typeService.list(typeQueryWrapper);
        return R.ok().data("typeList",typeList);
    }

    @GetMapping("tagList/{tId}")
    public R getTypeTagList(@PathVariable String tId) {
        if(tId.length()>0) {
            QueryWrapper<RedCategoryTag> typeQueryWrapper = new QueryWrapper<>();
            typeQueryWrapper.orderByAsc("sort");
            List<RedCategoryTag> tagList = typeService.getInterviewTypeTagList(tId);
            return R.ok().data("tagList",tagList);
        } else {
            return R.error("参数错误，请重新尝试");
        }
    }

    @GetMapping("addGood/{qId}")
    public R addGood(@PathVariable String qId, HttpServletRequest request) {
        if (qId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                int count = goodService.updateGoodState(uId, qId);
                if (count <= 0) {
                    RedInterviewGood good = new RedInterviewGood();
                    good.setQid(qId);
                    good.setUid(uId);
                    if (goodService.save(good)) {
                        questionService.updateGoodCount(true, qId);
                        return R.ok().data("goodqustion", true);
                    }
                } else {
                    questionService.updateGoodCount(true, qId);
                    return R.ok().data("goodqustion", true);
                }
            } else {
                return R.error("请登录以后在点赞哈！");
            }
        }
        return R.error("点赞失败，请稍后重试哈！");
    }

    @GetMapping("cancleGood/{qId}")
    public R cancleGood(@PathVariable String qId, HttpServletRequest request) {
        if (qId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                QueryWrapper<RedInterviewGood> goodQueryWrapper = new QueryWrapper<>();
                goodQueryWrapper.eq("qid", qId);
                goodQueryWrapper.eq("uid", uId);
                if (goodService.remove(goodQueryWrapper)) {
                    questionService.updateGoodCount(false, qId);
                    return R.ok().data("goodqustion", false);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("取消点赞失败，请稍后重试哈！");
    }

    @GetMapping("addCollect/{qId}")
    public R addCollect(@PathVariable String qId, HttpServletRequest request) {
        if (qId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                int count = collectService.updateCollectState(uId, qId);
                if (count <= 0) {
                    RedInterviewCollect collect = new RedInterviewCollect();
                    collect.setQid(qId);
                    collect.setUid(uId);
                    if (collectService.save(collect)) {
                        questionService.updateCollectCount(true, qId);
                        return R.ok().data("goodqustion", true);
                    }
                } else {
                    questionService.updateGoodCount(true, qId);
                    return R.ok().data("result", true);
                }
            } else {
                return R.error("请登录以后在收藏哈！");
            }
        }
        return R.error("收藏失败，请稍后重试哈！");
    }

    @GetMapping("cancleCollect/{qId}")
    public R cancleCollect(@PathVariable String qId, HttpServletRequest request) {
        if (qId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                QueryWrapper<RedInterviewCollect> collectQueryWrapper = new QueryWrapper<>();
                collectQueryWrapper.eq("qid", qId);
                collectQueryWrapper.eq("uid", uId);
                if (collectService.remove(collectQueryWrapper)) {
                    questionService.updateGoodCount(false, qId);
                    return R.ok().data("result", false);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("取消收藏失败，请稍后重试哈！");
    }

    @GetMapping("status/{qid}")
    public R getInterviewUserStatus(@PathVariable String qid, HttpServletRequest request) {
        if (qid.length()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                RedUserStateVo status = questionService.getUserStatus(qid,uId);
                return R.ok().data("status",status);
            }
        }
        RedUserStateVo status = new RedUserStateVo();
        return R.ok().data("status",status);
    }

    @GetMapping("deleteQuestion/{qId}")
    public R deleteQuestion(@PathVariable String qId, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (qId.length()>0 && uId.length()>0) {
            QueryWrapper<RedInterviewAnswer> replyWrapper = new QueryWrapper<>();
            replyWrapper.eq("qid", qId);
            List<RedInterviewAnswer> replyList = answerService.list(replyWrapper);
            if (replyList.size()>0) {
                QueryWrapper<RedAskQuestion> questionWrapper = new QueryWrapper<>();
                questionService.updateQustionState(qId,uId,99);
                return R.ok().data("sucess",false).data("tips","有回答的问题不支持删除，已经提交删除申请,审核成功后删除哈");
            }
            QueryWrapper<RedInterviewQuestion> questionWrapper = new QueryWrapper<>();
            questionWrapper.eq("uid", uId);
            questionWrapper.eq("id", qId);
            if(questionService.remove(questionWrapper)) {
                return R.ok().data("sucess",true);
            } else {
                return R.error("删除回答失败，请重新尝试哈！");
            }
        }
        return R.error("参数异常，请重新尝试哈！");
    }

    @GetMapping("deleteAnswer/{qId}")
    public R deleteQuestionReply(@PathVariable String qId, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (qId.length()>0 && uId.length()>0) {
            QueryWrapper<RedInterviewAnswer> replyWrapper = new QueryWrapper<>();
            replyWrapper.eq("uid", uId);
            replyWrapper.eq("id", qId);
            if(answerService.remove(replyWrapper)) {
                return R.ok().data("rId",qId);
            } else {
                return R.error("删除题解失败，请重新尝试哈！");
            }
        }
        return R.error("参数异常，请重新尝试哈！");
    }

    @GetMapping("addAnswerCollect/{aId}")
    public R addAnswerCollect(@PathVariable String aId, HttpServletRequest request) {
        if (aId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                int count = answerCollectService.updateCollectState(uId, aId);
                if (count <= 0) {
                    RedInterviewAnswerCollect collect = new RedInterviewAnswerCollect();
                    collect.setAid(aId);
                    collect.setUid(uId);
                    if (answerCollectService.save(collect)) {
                        return R.ok().data("goodqustion", true);
                    }
                } else {
                    return R.ok().data("result", true);
                }
            } else {
                return R.error("请登录以后在收藏哈！");
            }
        }
        return R.error("收藏失败，请稍后重试哈！");
    }

    @GetMapping("cancleAnswerCollect/{aId}")
    public R cancleAnswerCollect(@PathVariable String aId, HttpServletRequest request) {
        if (aId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                QueryWrapper<RedInterviewAnswerCollect> collectQueryWrapper = new QueryWrapper<>();
                collectQueryWrapper.eq("aid", aId);
                collectQueryWrapper.eq("uid", uId);
                if (answerCollectService.remove(collectQueryWrapper)) {
                    return R.ok().data("result", false);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("取消点赞失败，请稍后重试哈！");
    }
}
