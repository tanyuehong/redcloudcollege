package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedMessageCommentReply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedMessageReplyVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-05-12
 */
public interface RedMessageCommentReplyService extends IService<RedMessageCommentReply> {

    RedMessageReplyVo getMessageCommentReplyOne(String mid);

    void addReplyGoodCount(String mid);

    void prepReplyGoodCount(String mid);

}
