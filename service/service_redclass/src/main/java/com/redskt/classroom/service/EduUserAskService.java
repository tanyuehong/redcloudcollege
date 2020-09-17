package com.redskt.classroom.service;

import com.redskt.classroom.entity.EduUserAsk;
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
public interface EduUserAskService extends IService<EduUserAsk> {

    Boolean saveUserAsk(EduUserAsk userAsk);

    RedClassAskQuestionVo getQustionDetail(String tId);

    List<RedClassAskQuestionVo> getHomeAskQustionList();
}
