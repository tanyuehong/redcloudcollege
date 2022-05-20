package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedClassBlogDetailVo;
import com.redskt.classroom.entity.vo.RedMessageDtailVo;
import com.redskt.classroom.entity.vo.RedUserStateVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-05-10
 */
public interface RedMessageService extends IService<RedMessage> {

    List<RedClassBlogDetailVo> getRedmessageDetailList(int size, int type, String uid);

    RedMessageDtailVo getRedMessageDetail(String mId);

    int updateReadCount(String mid);

    int updateGoodCount(boolean isAdd,String mid);

    RedUserStateVo getUserStatus(String mid, String uid);

}
