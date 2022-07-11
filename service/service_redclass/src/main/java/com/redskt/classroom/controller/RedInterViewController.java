package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.RedAskType;
import com.redskt.classroom.entity.RedBlogType;
import com.redskt.classroom.entity.RedCategoryTag;
import com.redskt.classroom.entity.vo.RedCategoryTagVo;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
import com.redskt.classroom.service.RedAskService;
import com.redskt.classroom.service.RedAskTypeService;
import com.redskt.classroom.service.RedBlogTypeService;
import com.redskt.classroom.service.RedCategoryTagService;
import com.redskt.commonutils.R;
import com.redskt.commonutils.RequestParmUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home/interview")
public class RedInterViewController {

    @Autowired
    private RedBlogTypeService typeService;

    @Autowired
    private RedAskTypeService askTypeService;

    @Autowired
    private RedCategoryTagService tagService;

    @Autowired
    private RedAskService userAskService;

    @PostMapping("index")
    public R getInterviewIndex(@RequestBody Map parameterMap) {
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

        QueryWrapper<RedBlogType> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("bsort");
        wrapper.ne("type", "focus");
        List<RedBlogType> typeList = typeService.list(wrapper);

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

        return R.ok().data("list", list).data("qustionType", askList).data("tagList", tagList).data("typeList",typeList);
    }

}
