package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedInterviewQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedInterviewQuestionVo;
import com.redskt.classroom.entity.vo.RedUserStateVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-14
 */
public interface RedInterviewQuestionService extends IService<RedInterviewQuestion> {

    List<RedInterviewQuestionVo> getHomeInterviewQustionList(String sort, String tag);

    void updateQuestionReadCount(String qId,int readCount);

    RedInterviewQuestionVo getQustionDetail(String qId);

    int updateGoodCount(boolean isAdd,String qId);

    int updateCollectCount(boolean isAdd,String qId);

    RedUserStateVo getUserStatus(String qid, String uid);

}
