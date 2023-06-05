package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.vo.RedCommentReplyVo;
import com.redskt.classroom.entity.vo.RedCommentVo;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import com.redskt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/blog")
public class RedBlogUserController {

    @Autowired
    private RedBlogCommentService commentService;

    @Autowired
    private RedBlogCommentReplyService replyService;

    @Autowired
    private RedBlogCommentGoodService goodService;

    @Autowired
    private RedBlogCollectService collectService;

    @Autowired
    private RedBlogDetailService detailService;

    @PostMapping("addNewBlog")
    public R addNewBlog(@RequestBody RedBlogDetail blogDetail, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if(uId.length()>0) {
            blogDetail.setAuid(uId);
            if(blogDetail.getId()!=null && blogDetail.getId().length()>0) {
                if(detailService.updateById(blogDetail)) {
                    return R.ok().data("blog",blogDetail);
                } else {
                    return R.error("内容不存在哦~");
                }
            } else {
                if(detailService.save(blogDetail)) {
                    return R.ok().data("blog",blogDetail);
                } else {
                    return R.error("新建文章失败，请重新尝试~");
                }
            }
        } else  {
            return R.error("参数验证失败！");
        }
    }

    @PostMapping("commet/submit")
    public R submitReplyComment(@RequestBody RedBlogComment comment, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if(uId.length()>0 && comment.getUid().length()>0 && uId.equals(comment.getUid())) {
            if (commentService.save(comment)) {
                RedCommentVo curComment = commentService.getBlogCommentOne(comment.getId());
                return R.ok().data("comment",curComment);
            } else  {
                return R.error("评论文章失败，请重新尝试！");
            }
        } else  {
            return R.error("参数验证失败！");
        }
    }

    @PostMapping("commet/submitReply")
    public R submitReplyComment(@RequestBody RedBlogCommentReply reply, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if(uId.length()>0 && reply.getUid().length()>0 && uId.equals(reply.getUid())) {
            if (replyService.save(reply)) {
                RedCommentReplyVo curReply = replyService.getBlogCommentReplyOne(reply.getId());
                return R.ok().data("reply",curReply);
            } else  {
                return R.error("评论文章失败，请重新尝试！");
            }
        } else  {
            return R.error("参数验证失败！");
        }
    }

    @GetMapping("comment/addCommentGood/{cId}/{type}")
    public R addCommentGood(@PathVariable String cId,@PathVariable int type, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (uId.length() > 0 && cId.length()>0) {
            if(goodService.updateCommentGoodState(uId,cId, type==1?1:2)<=0) {
                RedBlogCommentGood good = new RedBlogCommentGood();
                good.setUid(uId);
                good.setCid(cId);
                good.setGtype(type==1?1:2);
                goodService.save(good);
            }
            if(type == 1) {
                commentService.addCommentGoodCount(cId);
            } else {
                replyService.addCommentReplyGoodCount(cId);
            }
            return R.ok().data("goodState",1);
        } else {
            return R.error("参数验证失败！");
        }
    }

    @GetMapping("comment/cancleCommentGood/{cId}/{type}")
    public R cancleGood(@PathVariable String cId,@PathVariable int type, HttpServletRequest request) {
        if (cId.length() > 0) {
            String uId = TokenManager.getMemberIdByJwtToken(request);
            if (uId.length() > 0) {
                QueryWrapper<RedBlogCommentGood> goodQueryWrapper = new QueryWrapper<>();
                goodQueryWrapper.eq("cid", cId);
                goodQueryWrapper.eq("uid", uId);
                goodQueryWrapper.eq("gtype", type == 1 ? 1:2);
                if (goodService.remove(goodQueryWrapper)) {
                    if(type == 1) {
                        commentService.prepCommentGoodCount(cId);
                    } else {
                        replyService.prepCommentReplyGoodCount(cId);
                    }
                    return R.ok().data("goodqustion", false);
                }
            } else {
                return R.error("登录信息异常，请重新登录后尝试！");
            }
        }
        return R.error("取消点赞失败，请稍后重试哈！");
    }

    @GetMapping("collect/addBlogCollect/{bId}")
    public R addBlogCollect(@PathVariable String bId, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (uId.length() > 0 && bId.length()>0) {
            if(collectService.updateBlogCollectState(bId,uId)<=0) {
                RedBlogCollect collect = new RedBlogCollect();
                collect.setUid(uId);
                collect.setBid(bId);
                collect.setType(1);
                collectService.save(collect);
            }
            return R.ok().data("sucess",true);
        } else {
            return R.error("参数验证失败！");
        }
    }

    @GetMapping("collect/cancleBlogCollect/{bId}")
    public R cancleBlogCollect(@PathVariable String bId, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (uId.length() > 0 && bId.length()>0) {
            QueryWrapper<RedBlogCollect> collectQueryWrapper = new QueryWrapper<>();
            collectQueryWrapper.eq("bid", bId);
            collectQueryWrapper.eq("uid", uId);
            collectService.remove(collectQueryWrapper);
            return R.ok().data("sucess",true);
        } else {
            return R.error("参数验证失败！");
        }
    }
}
