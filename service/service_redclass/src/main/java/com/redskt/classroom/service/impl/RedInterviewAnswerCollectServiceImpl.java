package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedInterviewAnswerCollect;
import com.redskt.classroom.mapper.RedInterviewAnswerCollectMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redskt.classroom.service.RedInterviewAnswerCollectService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-08-16
 */
@Service
public class RedInterviewAnswerCollectServiceImpl extends ServiceImpl<RedInterviewAnswerCollectMapper, RedInterviewAnswerCollect> implements RedInterviewAnswerCollectService {

    @Override
    public int updateCollectState(String uid,String aId) {
        return  baseMapper.updateCollectState(uid, aId);
    }
}
