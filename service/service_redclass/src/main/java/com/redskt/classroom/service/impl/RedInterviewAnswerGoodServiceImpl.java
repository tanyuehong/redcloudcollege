package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedInterviewAnswerGood;
import com.redskt.classroom.mapper.RedInterviewAnswerGoodMapper;
import com.redskt.classroom.service.RedInterviewAnswerGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-08-12
 */
@Service
public class RedInterviewAnswerGoodServiceImpl extends ServiceImpl<RedInterviewAnswerGoodMapper, RedInterviewAnswerGood> implements RedInterviewAnswerGoodService {

    @Override
    public int updateAnswerGoodState(String uid,String aId) {
        return  baseMapper.updateAnswerGoodState(uid, aId);
    }
}
