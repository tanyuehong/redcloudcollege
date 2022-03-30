package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedReplyCommentGood;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-03-17
 */
public interface RedReplyCommentGoodService extends IService<RedReplyCommentGood> {

     int updateReplyCommentGoodState(String uid,String cId);

}
