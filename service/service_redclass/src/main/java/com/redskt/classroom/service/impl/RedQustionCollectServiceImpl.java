package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedQustionCollect;
import com.redskt.classroom.mapper.RedQustionCollectMapper;
import com.redskt.classroom.service.RedQustionCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-02-09
 */
@Service
public class RedQustionCollectServiceImpl extends ServiceImpl<RedQustionCollectMapper, RedQustionCollect> implements RedQustionCollectService {
    @Override
    public int updateQustionCollectState(String uid,String qId) {
        return baseMapper.updateCollectState(uid,qId);
    }
}
