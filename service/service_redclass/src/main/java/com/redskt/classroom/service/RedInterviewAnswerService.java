package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedInterviewAnswer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedClassAnswerVo;
import com.redskt.classroom.entity.vo.RedClassReplyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-21
 */
public interface RedInterviewAnswerService extends IService<RedInterviewAnswer> {

    RedClassReplyVo getUserLasterReply(@Param("uid")  String uid);

    List<RedClassAnswerVo> getInterviewAnswerList(String qId, int sortType);
}
