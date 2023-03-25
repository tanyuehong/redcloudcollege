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

    List<RedInterviewQuestionVo> getHotInterviewQustionList(String qType,String qId);

    List<RedInterviewQuestionVo> getPositionQuestionList(int sort,String pId,String sId,int orderType);

    void updateQuestionReadCount(String qId,int readCount);

    RedInterviewQuestionVo getQuestionDetail(String qId);

    int updateGoodCount(boolean isAdd,String qId);

    int updateCollectCount(boolean isAdd,String qId);

    RedUserStateVo getUserStatus(String qid, String uid);

    int updateQustionState(String qId,String uId,int state);

    void updateMeetType(String qId,int type);

}
