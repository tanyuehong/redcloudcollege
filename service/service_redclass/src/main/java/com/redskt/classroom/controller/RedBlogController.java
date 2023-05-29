package com.redskt.classroom.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.vo.RedCommentVo;
import com.redskt.classroom.entity.vo.RedCategoryTagVo;
import com.redskt.classroom.entity.vo.RedUserStateVo;
import com.redskt.classroom.entity.vo.RedClassBlogDetailVo;
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

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2021-11-17
 */
@RestController
@RequestMapping("/home/pratice")
public class RedBlogController {

    @Autowired
    private RedBlogTypeService typeService;

    @Autowired
    private RedBlogDetailService blogService;

    @Autowired
    private RedBlogCommentService commentService;

    @Autowired
    private RedBlogGoodService goodService;

    @Autowired
    private RedCategoryTagService tagService;


    @PostMapping("index")
    public R index(@RequestBody Map parameterMap) {
        parameterMap = RequestParmUtil.transToMAP(parameterMap);
        String type = (String) parameterMap.get("type");
        String sort = (String) parameterMap.get("sort");
        String tagId  = (String) parameterMap.get("tag");
        String token  = (String) parameterMap.get("token");

        String uId = TokenManager.getUserFromToken(token);

        int sortIndex = 1;
        if(sort!=null) {
            if (sort.equals("recommand")) {
                sortIndex = 2;
            }
            if (sort.equals("latest")) {
                sortIndex = 1;
            }
            if (sort.equals("hot")) {
                sortIndex = 3;
            }
        }

        QueryWrapper<RedBlogType> wrapper = new QueryWrapper<>();
        if(uId.length()<=0) {
            wrapper.ne("type", "focus");
        }
        wrapper.orderByAsc("bsort");
        List<RedBlogType> typeList = typeService.list(wrapper);

        if(type!=null && type.equals("all")) {  // 类型为null时 不需要过滤类型
            type = null;
        }

        List<RedClassBlogDetailVo> blogList = new ArrayList<>();
        if(type!=null && type.equals("focus")) {
            blogList = blogService.getFocusBlogList(uId,sortIndex,20);
        } else {
            blogList = blogService.getIndexBlogList(type,tagId,sortIndex,20);
        }

        for (int i=0;i<blogList.size();i++) {
            RedClassBlogDetailVo detail = blogList.get(i);
            if (detail.getDescrb().length()>150) {
                detail.setDescrb(detail.getDescrb().substring(0,150)+"...");
            }
        }
        List<RedCategoryTagVo> tagList = new ArrayList<>();
        if(type!=null && type.length()>0) {
            tagList =  tagService.getBlogTypeTagList(type);
            if(tagList.size()>0) {
                RedCategoryTagVo allTag = new RedCategoryTagVo();
                allTag.setName("全部");
                allTag.setId("all");
                tagList.add(0,allTag);
            }
        }
        return R.ok().data("typeList",typeList).data("tagList",tagList).data("blogList",blogList);
    }

    @GetMapping("getBlog/{bId}")
    public R getBlog(@PathVariable String bId) {
        if (bId.length()>0) {
            RedBlogDetail blogDetail = blogService.getById(bId);
            if(blogDetail == null) {
                return R.error("内容不存在哦~");
            }
            return R.ok().data("blog", blogDetail);
        } else {
            return R.error("参数不合法，请验证");
        }
    }

    @GetMapping("getBlogTypeList")
    public R getBlogTypeList() {
        QueryWrapper<RedBlogType> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("bsort");
        List<RedBlogType> typeList = typeService.list(wrapper);
        return R.ok().data("typeList",typeList);
    }

    @GetMapping("getDetail/{pId}")
    public R index(@PathVariable String pId) {
        if (pId.length()>0) {
            RedClassBlogDetailVo detail = blogService.getRedClassBlogDetail(pId);
            List<RedClassBlogDetailVo> blogList = blogService.getRedBlogDetailList(6, 3, null);
            for (int i = 0; i < blogList.size(); i++) {
                RedClassBlogDetailVo fdetail = blogList.get(i);
                if (fdetail.getDescrb().length() > 150) {
                    fdetail.setDescrb(fdetail.getDescrb().substring(0, 150) + "...");
                }
            }
            return R.ok().data("pitem", detail).data("blogList", blogList);
        } else {
            return R.error("参数不合法，请验证");
        }
    }

   
    @GetMapping("getCommentList/{bId}/{type}")
    public R getCommentList(@PathVariable String bId,@PathVariable int type) {
        if (bId.length()>0) {
            List<RedCommentVo> commentList = commentService.getRedBlogCommentList(bId,6,type);
            return R.ok().data("comments",commentList);
        } else {
            return R.error("参数不合法，请验证");
        }
    }

    @GetMapping("status/{bid}")
    public R getBlogUserStatus(@PathVariable String bid, HttpServletRequest request) {
        if (bid.length()>0) {
            blogService.updateReadCount(bid);
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                RedUserStateVo status = blogService.getBlogUserStatus(bid,uId);
                return R.ok().data("status",status);
            }
        }
        RedUserStateVo status = new RedUserStateVo();
        return R.ok().data("status",status);
    }

    @GetMapping("addGood/{praticeId}")
    public R addGood(@PathVariable String praticeId, HttpServletRequest request) {
        if (praticeId.length()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                int count = goodService.updateGoodState(uId,praticeId);
                if (count<=0) {
                    RedBlogGood good = new RedBlogGood();
                    good.setBid(praticeId);
                    good.setUid(uId);
                    if(goodService.save(good)) {
                        blogService.updateBlogGoodCount(true,praticeId);
                        return R.ok().data("good", true);
                    }
                } else {
                    blogService.updateBlogGoodCount(true,praticeId);
                    return R.ok().data("good", true);
                }
            } else {
               return R.error("请您登录以后在点赞该文章哈！");
            }
        }
        return R.error("点赞失败，请稍后重试哈！");
    }

    @GetMapping("cancleGood/{praticeId}")
    public R cancleGood(@PathVariable String praticeId, HttpServletRequest request) {
        if (praticeId.length()>0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                QueryWrapper<RedBlogGood> goodQueryWrapper = new QueryWrapper<>();
                goodQueryWrapper.eq("bid", praticeId);
                goodQueryWrapper.eq("uid", uId);
                if(goodService.remove(goodQueryWrapper)) {
                    blogService.updateBlogGoodCount(false,praticeId);
                    return R.ok().data("good",false);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("取消点赞失败，请稍后重试哈！");
    }
}

