package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedInterviewQuestionEveryday;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.admin.vo.RedInterViewEveryDayQuestionVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2023-03-20
 */
public interface RedInterviewQuestionEverydayService extends IService<RedInterviewQuestionEveryday> {

    List<RedInterViewEveryDayQuestionVo> getInterViewEveryQuestionList(String date);
}
