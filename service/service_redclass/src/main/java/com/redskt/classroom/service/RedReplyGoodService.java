package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedReplyGood;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-02-11
 */
public interface RedReplyGoodService extends IService<RedReplyGood> {
    int updateReplyGoodState(String uid,String qId);

    List<RedReplyGood> getUserReplyGoodState(List<String> rIds,String uId);

}
