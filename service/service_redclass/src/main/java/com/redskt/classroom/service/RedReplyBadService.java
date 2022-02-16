package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedReplyBad;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-02-15
 */
public interface RedReplyBadService extends IService<RedReplyBad> {
    int updateReplyBadState(String uid,String qId);
}
