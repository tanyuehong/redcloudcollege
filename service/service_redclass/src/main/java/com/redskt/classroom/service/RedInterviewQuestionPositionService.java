package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedInterviewQuestionPosition;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.admin.vo.RedInterviewQuestionPositionVo;
import com.redskt.classroom.entity.vo.RedInterviewQuestionVo;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-10-20
 */
public interface RedInterviewQuestionPositionService extends IService<RedInterviewQuestionPosition> {

    List<RedInterviewQuestionPositionVo> getQuestionPositionClassifyList(String qId);
}
