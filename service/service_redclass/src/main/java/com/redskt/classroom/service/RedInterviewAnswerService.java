package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedInterviewAnswer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedClassAnswerVo;

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

    RedClassAnswerVo getUserLasterReply(String qid);

    int updateGoodCount(boolean isAdd,String aId);

    int updateState(String aId,int state);

    List<RedClassAnswerVo> getInterviewAnswerList(String qId, String uId, int sortType);
}
