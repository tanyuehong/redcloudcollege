package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedInterviewQuestionPosition;
import com.redskt.classroom.entity.admin.vo.RedInterviewQuestionPositionVo;
import com.redskt.classroom.mapper.RedInterviewQuestionPositionMapper;
import com.redskt.classroom.service.RedInterviewQuestionPositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-10-20
 */
@Service
public class RedInterviewQuestionPositionServiceImpl extends ServiceImpl<RedInterviewQuestionPositionMapper, RedInterviewQuestionPosition> implements RedInterviewQuestionPositionService {

    public List<RedInterviewQuestionPositionVo> getQuestionPositionClassifyList(String qId) {
        return baseMapper.getQuestionPositionClassifyList(qId);
    }
}
