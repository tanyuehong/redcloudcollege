package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.RedCategoryTag;
import com.redskt.classroom.entity.RedInterviewPositionTags;
import com.redskt.classroom.entity.RedQustionCollect;
import com.redskt.classroom.entity.admin.vo.RedInterviewPositionTagsVo;
import com.redskt.classroom.entity.vo.RedCategoryTagVo;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
import com.redskt.classroom.service.RedAskService;
import com.redskt.classroom.service.RedCategoryTagService;
import com.redskt.classroom.service.RedInterviewPositionTagsService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class RedTagsUserController {

    @Autowired
    private RedCategoryTagService tagService;

    @Autowired
    private RedInterviewPositionTagsService positionTagsService;

    @PostMapping("addTag")
    public R addTag(@RequestBody RedCategoryTag tag) {
        if (tag.getName().length() > 0 && tag.getDescrib().length() > 0) {
            QueryWrapper<RedCategoryTag> tagQueryWrapper = new QueryWrapper<>();
            tagQueryWrapper.eq("name", tag.getName());
            RedCategoryTag oldTag =  tagService.getOne(tagQueryWrapper);
            if(oldTag!=null) {
                return R.error("该标签已经存在，不要反复创建哦~");
            }
            if (tagService.save(tag)) {
                return R.okSucessTips("添加新标签成果哦~");
            } else {
                return R.error("添加新标签失败，请重新尝试！");
            }
        } else {
            return R.errorParam();
        }
    }

    @PostMapping("addTagPosition")
    public R addTagPosition(@RequestBody RedInterviewPositionTags tags) {
        if (tags.getTid().length() > 0 && tags.getPid().length() > 0) {
            QueryWrapper<RedInterviewPositionTags> tagsQueryWrapper = new QueryWrapper<>();
            tagsQueryWrapper.eq("tid", tags.getTid());
            tagsQueryWrapper.eq("pid", tags.getPid());
            RedInterviewPositionTags oldTag =  positionTagsService.getOne(tagsQueryWrapper);
            if(oldTag!=null) {
                return R.error("该标签已经绑定该职位，不要反复绑定哦~");
            }
            if (positionTagsService.save(tags)) {
                return R.okSucessTips("标签绑定职位成功哦~");
            } else {
                return R.error("标签绑定职位失败，请重新尝试！");
            }
        } else {
            return R.errorParam();
        }
    }


    @GetMapping("getTagList")
    public R getAskTagList() {
        QueryWrapper<RedCategoryTag> tagQueryWrapper = new QueryWrapper<>();
        tagQueryWrapper.orderByAsc("gmt_create");
        List<RedCategoryTag> tagList = tagService.list(tagQueryWrapper);
        return R.ok().data("tagList",tagList);
    }

    @GetMapping("getTagPositionList/{tId}")
    public R getTagPositionList(@PathVariable String tId) {
        List<RedInterviewPositionTagsVo> positionList = positionTagsService.getInterviewPositionTagsList(tId);
        return R.ok().data("positionList",positionList);
    }



}
