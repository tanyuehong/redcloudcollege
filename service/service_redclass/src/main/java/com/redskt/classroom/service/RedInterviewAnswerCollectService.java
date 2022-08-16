package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedInterviewAnswerCollect;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-08-16
 */
public interface RedInterviewAnswerCollectService extends IService<RedInterviewAnswerCollect> {

    int updateCollectState(String uid,String aId);
}
