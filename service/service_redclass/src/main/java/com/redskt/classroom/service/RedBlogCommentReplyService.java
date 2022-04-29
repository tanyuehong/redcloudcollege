package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedBlogCommentReply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedBlogCommentReplyVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-04-26
 */
public interface RedBlogCommentReplyService extends IService<RedBlogCommentReply> {

    RedBlogCommentReplyVo getBlogCommentReplyOne(String cid);

    void addCommentReplyGoodCount(String cid);

    void prepCommentReplyGoodCount(String cid);

}
