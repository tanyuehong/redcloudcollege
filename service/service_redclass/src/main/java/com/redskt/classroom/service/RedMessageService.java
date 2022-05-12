package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedMessageDtailVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-05-10
 */
public interface RedMessageService extends IService<RedMessage> {

    RedMessageDtailVo getRedMessageDetail(String mId);

    int updateReadCount(String mid);
}
