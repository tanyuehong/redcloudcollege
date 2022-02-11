package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedReplyGood;
import com.redskt.classroom.mapper.RedReplyGoodMapper;
import com.redskt.classroom.service.RedReplyGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-02-11
 */
@Service
public class RedReplyGoodServiceImpl extends ServiceImpl<RedReplyGoodMapper, RedReplyGood> implements RedReplyGoodService {
    @Override
    public int updateReplyGoodState(String uid,String qId) {
        return baseMapper.updateGoodState(uid,qId);
    }
}
