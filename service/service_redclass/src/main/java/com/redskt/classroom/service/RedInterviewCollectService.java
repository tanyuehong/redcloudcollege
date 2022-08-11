package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedInterviewCollect;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-08-11
 */
public interface RedInterviewCollectService extends IService<RedInterviewCollect> {

    int updateCollectState(String uid,String qId);

}
