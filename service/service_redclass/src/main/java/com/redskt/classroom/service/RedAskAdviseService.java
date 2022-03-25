package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedAskAdvise;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-03-25
 */
public interface RedAskAdviseService extends IService<RedAskAdvise> {
    int updateQustionAdvise(String qId, String uId,int type,String content);
}
