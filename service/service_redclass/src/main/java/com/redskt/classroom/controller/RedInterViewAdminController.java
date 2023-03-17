package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.admin.vo.RedInterviewQuestionPositionVo;
import com.redskt.classroom.entity.vo.*;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import com.redskt.commonutils.RequestParmUtil;
import com.redskt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/interview/admin")
public class RedInterViewAdminController {

    @Autowired
    private RedInterviewTypeService typeService;

    @Autowired
    private RedInterviewQuestionService questionService;

    @Autowired
    private RedInterviewQuestionPositionService questionPositionService;

    @Autowired
    private RedInterviewPositionClassifyService classifyService;

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
    private RedUserService userService;


    @GetMapping("questionList")
    public R getQuestionList() {
        QueryWrapper<RedInterviewQuestion> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.orderByAsc("gmt_create");
//            PageHelper.startPage(pId, 20);
        List<RedInterviewQuestion> questionList = questionService.list(questionQueryWrapper);
//            PageInfo page = new PageInfo(typeList);
        return R.ok().data("questionList", questionList);
    }

    @GetMapping("positionList")
    public R getInterviewIndex() {
        QueryWrapper<RedInterviewType> typeQueryWrapper = new QueryWrapper<>();
        typeQueryWrapper.orderByAsc("gmt_create");

//            PageHelper.startPage(pId, 20);
        List<RedInterviewType> typeList = typeService.list(typeQueryWrapper);
//            PageInfo page = new PageInfo(typeList);
        return R.ok().data("positionList", typeList);
    }

    @GetMapping("positionClassifyList/{pId}")
    public R positionClassifyList(@PathVariable String pId) {
        if (pId.length()>0) {
            QueryWrapper<RedInterviewPositionClassify> classifyQueryWrapper = new QueryWrapper<>();
            classifyQueryWrapper.eq("pid",pId);
            classifyQueryWrapper.orderByAsc("sort");

//            PageHelper.startPage(pId, 20);
            List<RedInterviewPositionClassify> classifyList = classifyService.list(classifyQueryWrapper);
//            PageInfo page = new PageInfo(typeList);
            return R.ok().data("positionClassifyList", classifyList);
        }
        return R.errorParam();
    }

    @GetMapping("questionClassifyList/{qId}")
    public R questionClassifyList(@PathVariable String qId) {
        if (qId.length()>0) {
            List<RedInterviewQuestionPositionVo> questionPositionClassifyList = questionPositionService.getQuestionPositionClassifyList(qId);
            return R.ok().data("questionClassifyList", questionPositionClassifyList);
        }
        return R.errorParam();
    }

    @PostMapping("submitClassify")
    public R submitClassify(@RequestBody RedInterviewPositionClassify classify,HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (uId.length()>0 && this.checkIsAdmin(uId)) {
            if(classifyService.save(classify)) {
                return R.okSucessTips("添加成功");
            }
            return R.error("存储失败,请重试哈~");
        } else {
            return  R.error("没有对应的权限~");
        }
    }

    @PostMapping("submitQuestionPosition")
    public R submitQuestionPosition(@RequestBody RedInterviewQuestionPosition questionPosition,HttpServletRequest request) {
        if (questionPosition.getPid()==null ||questionPosition.getPid().length() == 0 ||
                questionPosition.getQid()==null ||questionPosition.getQid().length() == 0) {
            return R.errorParam();
        }
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (uId.length()>0 && this.checkIsAdmin(uId)) {
            QueryWrapper<RedInterviewQuestionPosition> positionQueryWrapper = new QueryWrapper<>();
            positionQueryWrapper.eq("qid",questionPosition.getQid());
            positionQueryWrapper.eq("pid",questionPosition.getPid());
            List<RedInterviewQuestionPosition> questionPositionList = questionPositionService.list(positionQueryWrapper);
            if (questionPositionList.size()>0) {
                return R.error("本题目已经绑定当前职位，请在列表页面编辑修改哈～");
            }
            if(questionPositionService.save(questionPosition)) {
                return R.okSucessTips("添加题目职位信息成功");
            }
            return R.error("存储失败,请重试哈~");
        } else {
            return  R.error("没有对应的权限~");
        }
    }

    @GetMapping("deleteClassify/{cid}")
    public R positionClassifyList(@PathVariable String cid,HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (cid.length()>0 && this.checkIsAdmin(uId)) {
            if (classifyService.removeById(cid)) {
                return R.okSucessTips("删除成功！");
            } else {
                return R.error("操作异常，请重新操作哦~");
            }
        }
        return R.errorParam();
    }

    private boolean checkIsAdmin(String uId) {
        QueryWrapper<RedClassUser> wrapper = new QueryWrapper<>();
        wrapper.select("id","username","authority");
        wrapper.eq("id",uId);
        RedClassUser eduUser = userService.getOne(wrapper);
        if (eduUser!=null && eduUser.getAuthority() == 100) {
            return true;
        }
        return false;
    }

    @PostMapping("getQuestionDetail")
    public R getQuestionDetail(@RequestBody Map parameterMap) {
        parameterMap = RequestParmUtil.transToMAP(parameterMap);
        String typeString= (String) parameterMap.get("type");
        Integer type = Integer.parseInt(typeString);

        String qId = (String) parameterMap.get("qId");
        String token = (String) parameterMap.get("token");
        String uId = TokenManager.getUserFromToken(token);


        if(qId.length()>0) {
            RedInterviewQuestionVo qDetail = questionService.getQustionDetail(qId);
            int readCount = qDetail.getReadcount() + 1;
            questionService.updateQuestionReadCount(qDetail.getQId(), readCount);

            QueryWrapper<RedInterviewType> typeQueryWrapper = new QueryWrapper<>();
            typeQueryWrapper.orderByAsc("sort");
            typeQueryWrapper.last("limit 4");
            List<RedInterviewType> positionList = typeService.list(typeQueryWrapper);

            RedInterviewType currentPosition = typeService.getById(qDetail.getQustype());
            List<RedInterviewQuestionVo> hotList = questionService.getHotInterviewQustionList(qDetail.getQustype(),qDetail.getQId());

            List<RedClassReplyVo> replyList = new ArrayList<>();
            if(type == 1) {
                List<RedClassAnswerVo> answerVoList = answerService.getInterviewAnswerList(qId,uId,1);
                return R.ok().data("qdetail", qDetail).data("dataList", answerVoList).data("positionList", positionList)
                        .data("currentPosition", currentPosition).data("hotList",hotList);
            } else if(type==2) {
                List<RedCommentVo> commentVoList = commentService.getRedCommentList(qId,uId,5,2);
                return R.ok().data("qdetail", qDetail).data("dataList", commentVoList).data("positionList", positionList)
                        .data("currentPosition", currentPosition).data("hotList",hotList);
            } else {
                return R.ok().data("qdetail", qDetail).data("dataList", replyList).data("positionList", positionList)
                        .data("currentPosition", currentPosition).data("hotList",hotList);
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
