package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedAskReply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedAskReplyVo;

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

    List<RedAskReplyVo> getHomeAskReplyList(String qId);

    int updateReplyGoodCount(boolean isAdd,String rId);

    int updateReplyBadCount(boolean isAdd,String rId);

}
