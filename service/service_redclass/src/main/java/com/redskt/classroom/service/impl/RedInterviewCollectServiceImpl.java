package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedInterviewCollect;
import com.redskt.classroom.mapper.RedInterviewCollectMapper;
import com.redskt.classroom.service.RedInterviewCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-08-11
 */
@Service
public class RedInterviewCollectServiceImpl extends ServiceImpl<RedInterviewCollectMapper, RedInterviewCollect> implements RedInterviewCollectService {

    @Override
    public int updateCollectState(String uid,String qId) {
        return baseMapper.updateCollectState(uid, qId);
    }
}
