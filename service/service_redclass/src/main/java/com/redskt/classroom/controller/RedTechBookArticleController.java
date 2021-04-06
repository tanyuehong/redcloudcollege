package com.redskt.classroom.controller;


import com.redskt.classroom.entity.EduTechnologyBook;
import com.redskt.classroom.entity.RedTechBookArticle;
import com.redskt.classroom.service.RedTechBookArticleService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2021-02-28
 */
@RestController
@RequestMapping("/home/book/article")
public class RedTechBookArticleController {

    @Autowired
    private RedTechBookArticleService articleService;

    @GetMapping("getBookArticleDetail/{id}")
    public R getBookDetail(@PathVariable String articleId) {
        if(articleId.length()>0) {
            RedTechBookArticle article = articleService.getById(articleId);
            return  R.ok().data("article",article);
        } else {
            return R.error("哦哦,参数错误哈");
        }
    }
}

