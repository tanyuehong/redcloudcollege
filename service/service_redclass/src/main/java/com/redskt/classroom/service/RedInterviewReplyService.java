package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedInterviewReply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedAskReplyVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-21
 */
public interface RedInterviewReplyService extends IService<RedInterviewReply> {

    List<RedInterviewReply> getInterViewReply(String qId);

}
