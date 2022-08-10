package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedInterviewGood;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-08-10
 */
public interface RedInterviewGoodService extends IService<RedInterviewGood> {

    int updateGoodState(String uid,String qId);
}
