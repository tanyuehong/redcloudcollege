package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedInterviewCommentReply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedCommentReplyVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-25
 */
public interface RedInterviewCommentReplyService extends IService<RedInterviewCommentReply> {

    RedCommentReplyVo getCommentReplyOne(String rId);
}
