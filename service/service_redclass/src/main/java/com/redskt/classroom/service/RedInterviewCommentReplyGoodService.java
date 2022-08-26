package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedInterviewCommentReplyGood;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-08-25
 */
public interface RedInterviewCommentReplyGoodService extends IService<RedInterviewCommentReplyGood> {

    int updateGoodState(String uid,String cId, int type);
}
