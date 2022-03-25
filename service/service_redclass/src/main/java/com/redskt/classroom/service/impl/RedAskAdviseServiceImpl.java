package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedAskAdvise;
import com.redskt.classroom.mapper.RedAskAdviseMapper;
import com.redskt.classroom.service.RedAskAdviseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-03-25
 */
@Service
public class RedAskAdviseServiceImpl extends ServiceImpl<RedAskAdviseMapper, RedAskAdvise> implements RedAskAdviseService {
    @Override
    public  int updateQustionAdvise(String qId, String uId,int type,String content) {
        return baseMapper.updateQustionAdvise(qId,uId,type,content);
    }
}
