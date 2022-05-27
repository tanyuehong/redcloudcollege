package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedReplyGood;
import com.redskt.classroom.entity.RedUserFocus;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-04-05
 */
public interface RedUserFocusService extends IService<RedUserFocus> {

    List<RedReplyGood> getUserFocusState(List<String> fIds, String uId);

    int updateUserFocus(String uid,String fId);
}
