package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.EduUserAsk;
import com.redskt.classroom.mapper.EduUserAskMapper;
import com.redskt.classroom.service.EduUserAskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-07-26
 */
@Service
public class EduUserAskServiceImpl extends ServiceImpl<EduUserAskMapper, EduUserAsk> implements EduUserAskService {

    @Override
    public Boolean saveUserAsk(EduUserAsk userAsk) {
        baseMapper.insert(userAsk);
        return true;
    }
}
