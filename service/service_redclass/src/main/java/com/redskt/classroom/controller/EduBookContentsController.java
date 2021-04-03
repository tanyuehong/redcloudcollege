package com.redskt.classroom.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.EduBookChapters;
import com.redskt.classroom.entity.EduBookContents;
import com.redskt.classroom.entity.EduTechnologyBook;
import com.redskt.classroom.service.EduBookChaptersService;
import com.redskt.classroom.service.EduBookContentsService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2021-02-28
 */
@RestController
@RequestMapping("/home/book/contents")
public class EduBookContentsController {
    @Autowired
    private EduBookContentsService contentsService;

    @Autowired
    private EduBookChaptersService chaptersService;

    @PostMapping("getBookContents")
    public R getBookDetail(@RequestBody String bookIdString) {
        JSONObject jsonObject = JSONObject.parseObject(bookIdString);
        String bookId = (String)jsonObject.get("bookId");
        if(bookId.length()>0) {
            // 1. 先查询章节
            QueryWrapper<EduBookChapters> chapterWrapper = new QueryWrapper<>();
            chapterWrapper.eq("book_id",bookId);
            List<EduBookChapters> chapters = chaptersService.list(chapterWrapper);

            // 2. 查询章节下的目录
            QueryWrapper<EduBookContents> wrapper = new QueryWrapper<>();
            wrapper.eq("book_id",bookId);
            List<EduBookContents> contents = contentsService.list(wrapper);

            // 3. 合并结果数据
            for(EduBookContents content : contents) {
                for(EduBookChapters chapter : chapters) {
                    if(content.getChapterId().equals(chapter.getId())) {
                        if(chapter.getChapterContents() == null) {
                            chapter.setChapterContents(new ArrayList<EduBookContents>());
                        }
                        chapter.getChapterContents().add(content);
                    }
                }
            }
            return  R.ok().data("chapterList",chapters);
        } else {
            return R.error("哦哦,参数错误哈");
        }
    }
}

