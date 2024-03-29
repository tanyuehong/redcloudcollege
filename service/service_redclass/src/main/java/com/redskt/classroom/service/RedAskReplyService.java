package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedAskReply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedClassReplyVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-01-21
 */
public interface RedAskReplyService extends IService<RedAskReply> {

    RedClassReplyVo getUserLasterReply(String uid);

    List<RedClassReplyVo> getHomeAskReplyList(String qId, int sortType);

    int updateReplyGoodCount(boolean isAdd,String rId);

    int updateReplyBadCount(boolean isAdd,String rId);

    int updateReplyState(String rId,int state);

}
