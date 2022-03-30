package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedAskWaring;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-03-25
 */
public interface RedAskWaringService extends IService<RedAskWaring> {
    int updateContentWarling(String wId, String uId,int type,String content);
}
