package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedInterviewGood;
import com.redskt.classroom.mapper.RedInterviewGoodMapper;
import com.redskt.classroom.service.RedInterviewGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-08-10
 */
@Service
public class RedInterviewGoodServiceImpl extends ServiceImpl<RedInterviewGoodMapper, RedInterviewGood> implements RedInterviewGoodService {

    @Override
    public int updateGoodState(String uid,String qId) {
        return baseMapper.updateGoodState(uid,qId);
    }
}
