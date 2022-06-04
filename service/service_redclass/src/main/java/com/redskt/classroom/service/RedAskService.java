package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedAskQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-07-26
 */
public interface RedAskService extends IService<RedAskQuestion> {

    Boolean saveUserAsk(RedAskQuestion userAsk);

    int updateQustionState(String qId,String uId,int state);

    void updateUserAskReadCount(String qId,int readCount);

    RedClassAskQuestionVo getQustionDetail(String qId);

    List<RedClassAskQuestionVo> getGoodQustionLists(int size,String uid);

    List<RedClassAskQuestionVo> getCollectQustionLists(int size,String uid);

    List<RedClassAskQuestionVo> getHomeAskQustionList(int type,String typeId);

    int updateQustionGoodCount(boolean isAdd,String qId);

    int updateQustionCollectCount(boolean isAdd,String qId);
}
