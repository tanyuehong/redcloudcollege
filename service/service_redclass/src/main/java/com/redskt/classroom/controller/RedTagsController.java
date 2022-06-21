package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.RedCategoryTag;
import com.redskt.classroom.entity.vo.RedCategoryTagVo;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
import com.redskt.classroom.service.RedAskService;
import com.redskt.classroom.service.RedCategoryTagService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home/tags")
public class RedTagsController {

    @Autowired
    private RedCategoryTagService tagService;

    @Autowired
    private RedAskService userAskService;


    @GetMapping("getAskTagList/{typeId}")
    public R getAskTagList(@PathVariable String typeId) {
        QueryWrapper<RedCategoryTag> tagQueryWrapper = new QueryWrapper<>();
        if(typeId.length()>0 && !typeId.equals("1")) {
            tagQueryWrapper.eq("asktype", typeId);
        }
        List<RedCategoryTag> tagList = tagService.list(tagQueryWrapper);
        return R.ok().data("tagList",tagList);
    }

    @GetMapping("getAllTagList")
    public R getAskTagList() {
        List<RedCategoryTagVo> tagList = tagService.getAllTagList();
        return R.ok().data("tagList",tagList);
    }

    @GetMapping("getTagDetail/{tId}")
    public R getTagDetail(@PathVariable String tId) {
        if(tId.length()>0) {
            QueryWrapper<RedCategoryTag> tagQueryWrapper = new QueryWrapper<>();
            tagQueryWrapper.eq("id", tId);
            RedCategoryTag tag = tagService.getOne(tagQueryWrapper);

            List<RedClassAskQuestionVo> qustionLists = userAskService.getTagQustionLists(tId);
            return R.ok().data("tag",tag).data("qustionList",qustionLists);
        } else {
            return R.error("参数异常，请重新尝试");
        }

    }
}
