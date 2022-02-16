package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedReplyBad;
import com.redskt.classroom.mapper.RedReplyBadMapper;
import com.redskt.classroom.service.RedReplyBadService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-02-15
 */
@Service
public class RedReplyBadServiceImpl extends ServiceImpl<RedReplyBadMapper, RedReplyBad> implements RedReplyBadService {
    @Override
    public int updateReplyBadState(String uid,String qId) {
        return baseMapper.updateBadState(uid,qId);
    }
}
