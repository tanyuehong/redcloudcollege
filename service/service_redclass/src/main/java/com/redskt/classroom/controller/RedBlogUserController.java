package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.entity.vo.RedCommentReplyVo;
import com.redskt.classroom.entity.vo.RedCommentVo;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import com.redskt.security.TokenManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Autowired
    private RedBlogDetailDraftService draftService;

    @Autowired
    private RedUserService userService;

    // 博文草稿的逻辑
    @PostMapping("addNewBlogDraft")
    public R addNewBlogDraft(@RequestBody RedBlogDetailDraft blogDetail, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if(uId.length()>0) {
            blogDetail.setAuid(uId);
            if(blogDetail.getId()!=null && blogDetail.getId().length()>0) {
                if(draftService.updateById(blogDetail)) {
                    return R.ok().data("blog",blogDetail);
                } else {
                    return R.error("内容不存在哦~");
                }
            } else {
                if(draftService.save(blogDetail)) {
                    return R.ok().data("blog",blogDetail);
                } else {
                    return R.error("新建文章失败，请重新尝试~");
                }
            }
        } else  {
            return R.error("参数验证失败！");
        }
    }

    @GetMapping("editBlog/{bId}")
    public R editBlog(@PathVariable String bId,HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if(bId!=null && bId.length()>0 && uId != null && uId.length()>0) {
            RedBlogDetailDraft blogDetail = draftService.getById(bId);
            if (blogDetail == null) {
                QueryWrapper<RedBlogDetail> detailQueryWrapper = new QueryWrapper<>();
                detailQueryWrapper.eq("id", bId);
                detailQueryWrapper.eq("auid", uId);
                RedBlogDetail redBlogDetail = detailService.getOne(detailQueryWrapper);
                if (redBlogDetail != null) {
                    String eId = redBlogDetail.getId();
                    redBlogDetail.setId(null);
                    blogDetail = new RedBlogDetailDraft();
                    BeanUtils.copyProperties(redBlogDetail, blogDetail);
                    blogDetail.setEid(eId);
                    if (draftService.save(blogDetail)) {
                        return R.ok().data("blog", blogDetail);
                    }
                }
            } else if (blogDetail.getAuid().equals(uId)) {
                return R.ok().data("blog", blogDetail);
            }
        }
        return R.errorParam();
    }

    @GetMapping("getBlogDraft/{bId}")
    public R getBlogDraft(@PathVariable String bId) {
        if (bId.length()>0) {
            RedBlogDetailDraft blogDetail = draftService.getById(bId);
            if(blogDetail == null) {
                return R.error("内容不存在哦~");
            }
            return R.ok().data("blog", blogDetail);
        } else {
            return R.error("参数不合法，请验证");
        }
    }

    @GetMapping("deleteDraftBlog/{bId}")
    public R deleteDraftBlog(@PathVariable String bId, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if(bId!=null && bId.length()>0 && uId != null && uId.length()>0) {
            if(draftService.removeDraft(bId,uId)) {
                return R.okSucessTips("删除草稿成功～");
            }
            return R.error("操作失败，请重新尝试");
        } else {
            return R.errorParam();
        }
    }

    @GetMapping("deleteBlog/{bId}")
    public R deleteBlog(@PathVariable String bId, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if(bId!=null && bId.length()>0 && uId != null && uId.length()>0) {
            if(detailService.removeBlog(bId,uId)) {
                return R.okSucessTips("删除文章成功～");
            }
            return R.error("操作失败，请重新尝试");
        } else {
            return R.errorParam();
        }
    }

    @PostMapping("addNewBlog")
    public R addNewBlog(@RequestBody RedBlogDetailDraft blogDetail, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if(uId != null && uId.length()>0 && blogDetail.checkIsCanSubmit()) {
            blogDetail.setAuid(uId);
            if(userService.checkIsAdmin(uId)) {
                blogDetail.setState(100);
            } else {
                blogDetail.setState(66);
            }

            List<String> mList = extractImageUrls(blogDetail.getContent());

            // 寻找在 array 中但不在 list 中的元素
            List<String> elementsOnlyInArray = new ArrayList<>(Arrays.asList(blogDetail.getSubmitImgList()));
            elementsOnlyInArray.removeAll(mList);
            for (String item : elementsOnlyInArray) { // 删除上传过程中 多余的图片
                String modifiedString = item.replace("https://static.redskt.com", "");
                File dest = new File("/home/redsktsource"+ modifiedString);
                dest.delete();
            }

            RedBlogDetail redBlogDetail = new RedBlogDetail();
            BeanUtils.copyProperties(blogDetail,redBlogDetail);

            if(blogDetail.getEid()!=null && blogDetail.getEid().length()>0) {
                QueryWrapper<RedBlogDetail> detailQueryWrapper = new QueryWrapper<>();
                detailQueryWrapper.eq("id", blogDetail.getEid());
                detailQueryWrapper.eq("auid", uId);
                if(detailService.update(redBlogDetail,detailQueryWrapper)) {
                    draftService.removeDraft(blogDetail.getId(),uId);
                    return R.okSucessTips("更新文章成功");
                } else {
                    return R.error("更新文章失败，请重新尝试~");
                }
            } else {
                if(detailService.save(redBlogDetail)) {
                    draftService.removeDraft(blogDetail.getId(),uId);
                    return R.okSucessTips("发布文章成功");
                } else {
                    return R.error("新建文章失败，请重新尝试~");
                }
            }
        } else  {
            return R.error("参数验证失败！");
        }
    }

    public static List<String> extractImageUrls(String markdownText) {
        List<String> imageUrls = new ArrayList<>();
        String regex = "!\\[.*?\\]\\((.*?)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(markdownText);

        while (matcher.find()) {
            String imageUrl = matcher.group(1);
            imageUrls.add(imageUrl);
        }

        return imageUrls;
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
