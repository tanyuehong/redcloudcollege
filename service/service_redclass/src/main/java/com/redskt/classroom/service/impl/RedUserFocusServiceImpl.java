package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedReplyGood;
import com.redskt.classroom.entity.RedUserFocus;
import com.redskt.classroom.mapper.RedUserFocusMapper;
import com.redskt.classroom.service.RedUserFocusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-04-05
 */
@Service
public class RedUserFocusServiceImpl extends ServiceImpl<RedUserFocusMapper, RedUserFocus> implements RedUserFocusService {

    @Override
    public List<RedReplyGood> getUserFocusState(List<String> fIds, String uId) {
        return baseMapper.getUserFocusState(fIds,uId);
    }

    @Override
    public int updateUserFocus(String uid,String fId) {
        return baseMapper.updateUserFocusState(uid,fId);
    }
}
