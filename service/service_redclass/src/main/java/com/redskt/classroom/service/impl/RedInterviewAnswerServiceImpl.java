package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedInterviewAnswer;
import com.redskt.classroom.entity.vo.RedClassAnswerVo;
import com.redskt.classroom.entity.vo.RedClassReplyVo;
import com.redskt.classroom.mapper.RedInterviewAnswerMapper;
import com.redskt.classroom.service.RedInterviewAnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-21
 */
@Service
public class RedInterviewAnswerServiceImpl extends ServiceImpl<RedInterviewAnswerMapper, RedInterviewAnswer> implements RedInterviewAnswerService {

    @Override
    public List<RedClassAnswerVo> getInterviewAnswerList(String qId, int sortType) {
        return baseMapper.getInterviewAnswerList(qId,5,5,sortType);
    }

    @Override
    public RedClassAnswerVo getUserLasterReply(String qid) {
        return  baseMapper.getUserLasterReply(qid);
    }
}
