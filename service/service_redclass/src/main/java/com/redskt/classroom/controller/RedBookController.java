package com.redskt.classroom.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.redskt.classroom.entity.RedClassBook;
import com.redskt.classroom.entity.vo.RedClassBookVo;
import com.redskt.classroom.service.EduTechnologyBookService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/home/book/")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class RedBookController {

    @Autowired
    private EduTechnologyBookService bookService;

    //查询前8条热门课程，查询前4条名师
    @GetMapping("getBooks")
    public R index() {
        //查询前8条热门课程
        QueryWrapper<RedClassBook> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<RedClassBookVo> bookList = bookService.getBookIndexInfo();

        return R.ok().data("bookList",bookList);
    }

    @GetMapping("getBookDetail/{bookId}")
    public R getBookDetail(@PathVariable String bookId) { if(bookId.length()>0) {
            RedClassBook book = bookService.getById(bookId);
            return  R.ok().data("book",book);
        } else {
            return R.error("哦哦,参数错误哈");
        }
    }
}

