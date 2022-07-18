package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedInterviewQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
import com.redskt.classroom.entity.vo.RedInterviewQuestionVo;

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

}
