package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedMessage;
import com.redskt.classroom.entity.vo.RedMessageDtailVo;
import com.redskt.classroom.mapper.RedMessageMapper;
import com.redskt.classroom.service.RedMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-05-10
 */
@Service
public class RedMessageServiceImpl extends ServiceImpl<RedMessageMapper, RedMessage> implements RedMessageService {
    @Override
    public RedMessageDtailVo getRedMessageDetail(String mId) {
        return baseMapper.getRedMessageDetail(mId);
    }
}
