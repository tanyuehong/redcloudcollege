package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedInterviewQuestionEveryday;
import com.redskt.classroom.entity.admin.vo.RedInterViewEveryDayQuestionVo;
import com.redskt.classroom.mapper.RedInterviewQuestionEverydayMapper;
import com.redskt.classroom.service.RedInterviewQuestionEverydayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2023-03-20
 */
@Service
public class RedInterviewQuestionEverydayServiceImpl extends ServiceImpl<RedInterviewQuestionEverydayMapper, RedInterviewQuestionEveryday> implements RedInterviewQuestionEverydayService {

    @Override
    public List<RedInterViewEveryDayQuestionVo> getInterViewEveryQuestionList(String date) {
        return baseMapper.getInterViewEveryQuestionList(date);
    }
}
