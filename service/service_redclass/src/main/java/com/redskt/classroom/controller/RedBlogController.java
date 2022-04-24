package com.redskt.classroom.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.vo.RedBlogCommentVo;
import com.redskt.classroom.entity.vo.RedClassBlogDetailVo;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import com.redskt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("index")
    public R index() {
        QueryWrapper<OpBlogType> wrapper = new QueryWrapper<>();
        wrapper.isNull("parentId");
        wrapper.orderByAsc("bsort");
        wrapper.last("limit 8");
        List<OpBlogType> typeList = typeService.list(wrapper);

        List<OpBlogType> subTypeList = new ArrayList<>();
        if (typeList.size()>0) {
            QueryWrapper<OpBlogType> subWrapper = new QueryWrapper<>();
            subWrapper.eq("parentId",typeList.get(0).getId());
            subTypeList = typeService.list(subWrapper);
        }

        QueryWrapper<OpBlogDetail> blogDetailQueryWrapper = new QueryWrapper<>();
        blogDetailQueryWrapper.select("id","title","type","good","faver","view_count","price","descrb");
        blogDetailQueryWrapper.orderByDesc("id");
        blogDetailQueryWrapper.last("limit 8");

        List<OpBlogDetail> blogList = blogService.list(blogDetailQueryWrapper);

        for (int i=0;i<blogList.size();i++) {
            OpBlogDetail detail = blogList.get(i);
            if (detail.getDescrb().length()>150) {
                detail.setDescrb(detail.getDescrb().substring(0,150)+"...");
            }
        }
        
        return R.ok().data("typeList",typeList).data("subTypeList",subTypeList).data("blogList",blogList);
    }

    @GetMapping("getDetail/{pId}")
    public R index(@PathVariable String pId) {
        if (pId.length()>0) {
            RedClassBlogDetailVo detail = blogService.getRedClassBlogDetail(pId);
            return R.ok().data("pitem",detail);
        } else {
            return R.error("参数不合法，请验证");
        }
    }

    @GetMapping("getCommentList/{bId}")
    public R getCommentList(@PathVariable String bId) {
        if (bId.length()>0) {
            List<RedBlogCommentVo> commentList = commentService.getRedBlogCommentList(bId,6,1);
            return R.ok().data("comments",commentList);
        } else {
            return R.error("参数不合法，请验证");
        }
    }

    @GetMapping("good/{praticeId}")
    public R getGoodState(@PathVariable String praticeId, HttpServletRequest request) {
        if (praticeId.length()>0) {
            blogService.updateReadCount(praticeId);
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length()>0) {
                QueryWrapper<RedBlogGood> goodQueryWrapper = new QueryWrapper<>();
                goodQueryWrapper.eq("bid", praticeId);
                goodQueryWrapper.eq("uid", uId);
                List<RedBlogGood> goodList = goodService.list(goodQueryWrapper);
                if (goodList.size() > 0) {
                    return R.ok().data("good", true);
                }
            }
        }
        return R.ok().data("good",false);
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

