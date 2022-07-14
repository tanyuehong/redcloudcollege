
package com.redskt.classroom.controller;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.*;
import com.redskt.classroom.service.*;
import com.redskt.commonutils.R;
import com.redskt.commonutils.RequestParmUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/interview")
public class RedInterViewUserController {

    @Autowired
    private RedInterviewTypeService typeService;

    @Autowired
    private RedInterviewQuestionService questionService;

    @Autowired
    private RedInterviewTagsService tagsService;


    @PostMapping("submit")
    public R submitQuestion(@RequestBody Map parameterMap) {
        parameterMap = RequestParmUtil.transToMAP(parameterMap);

        String uid      = (String) parameterMap.get("uid");
        String title    = (String) parameterMap.get("title");
        String content  = (String) parameterMap.get("content");
        String qustype  = (String) parameterMap.get("qustype");

        List<String> tags = JSON.parseArray((String) parameterMap.get("tagList"),String.class);

        RedInterviewQuestion question= new RedInterviewQuestion();
        question.setUid(uid);
        question.setTitle(title);
        question.setContent(content);
        question.setQustype(qustype);
        if (questionService.save(question)) {
            List<RedInterviewTags> tagsList = new ArrayList<>();
            for (int i=0;i<tags.size();i++) {
                RedInterviewTags tag = new RedInterviewTags();
                tag.setQid(question.getId());
                tag.setTid(tags.get(i));
                tagsList.add(tag);
            }
            tagsService.saveBatch(tagsList);
            return R.ok();
        } else  {
            return R.error("问题提交失败，请重新尝试！");
        }
    }
}
