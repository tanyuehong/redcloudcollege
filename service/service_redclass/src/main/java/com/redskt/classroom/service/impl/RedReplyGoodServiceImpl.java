package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedReplyGood;
import com.redskt.classroom.mapper.RedReplyGoodMapper;
import com.redskt.classroom.service.RedReplyGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public int updateReplyGoodState(String uid,String qId,int type) {
        return baseMapper.updateGoodState(uid,qId,type);
    }

    @Override
    public List<RedReplyGood> getUserReplyGoodState(List<String> rIds, String uId) {
        return baseMapper.getUserReplyGoodState(rIds,uId);
    }
}
