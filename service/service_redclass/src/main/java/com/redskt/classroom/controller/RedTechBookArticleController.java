package com.redskt.classroom.controller;


import com.redskt.classroom.entity.EduBookContents;
import com.redskt.classroom.entity.EduTechnologyBook;
import com.redskt.classroom.entity.RedTechBookArticle;
import com.redskt.classroom.service.EduBookContentsService;
import com.redskt.classroom.service.EduTechnologyBookService;
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
@RequestMapping("/home/book")
public class RedTechBookArticleController {

    @Autowired
    private RedTechBookArticleService articleService;

    @Autowired
    private EduTechnologyBookService bookService;

    @Autowired
    private EduBookContentsService contentsService;

    @GetMapping("getBookArticleDetail/{articleId}")
    public R getBookDetail(@PathVariable String articleId) {
        if(articleId.length()>0) {
            RedTechBookArticle article = articleService.getById(articleId);
            EduTechnologyBook book = bookService.getById(article.getBookId());
            EduBookContents contents = contentsService.getById(article.getContentId());
            return  R.ok().data("item",article).data("book",book).data("contents",contents);
        } else {
            return R.error("哦哦,参数错误哈");
        }
    }
}

