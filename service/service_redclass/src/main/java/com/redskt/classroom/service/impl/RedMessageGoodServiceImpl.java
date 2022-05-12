package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedMessageGood;
import com.redskt.classroom.mapper.RedMessageGoodMapper;
import com.redskt.classroom.service.RedMessageGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-05-12
 */
@Service
public class RedMessageGoodServiceImpl extends ServiceImpl<RedMessageGoodMapper, RedMessageGood> implements RedMessageGoodService {

    @Override
    public int updateGoodState(String uid,String mid) {
        return baseMapper.updateGoodState(uid,mid);
    }

}