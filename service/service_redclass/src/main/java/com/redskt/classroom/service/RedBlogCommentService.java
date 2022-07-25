package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedBlogComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedCommentVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-04-21
 */
public interface RedBlogCommentService extends IService<RedBlogComment> {
    List<RedCommentVo> getRedBlogCommentList(String bid, Integer rsize, int type);

    RedCommentVo getBlogCommentOne(String cid);

    void addCommentGoodCount(String cid);

    void prepCommentGoodCount(String cid);
}
