package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedInterviewAnswerGood;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-08-12
 */
public interface RedInterviewAnswerGoodService extends IService<RedInterviewAnswerGood> {

    int updateAnswerGoodState(String uid,String aId);
}
