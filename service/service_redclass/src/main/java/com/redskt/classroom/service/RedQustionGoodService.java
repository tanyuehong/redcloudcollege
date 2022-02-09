package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedQustionGood;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-02-07
 */
public interface RedQustionGoodService extends IService<RedQustionGood> {
    int updateQustionGoodState(String uid,String qId);
}
