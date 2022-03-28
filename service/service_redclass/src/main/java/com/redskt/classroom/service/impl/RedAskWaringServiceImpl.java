package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedAskWaring;
import com.redskt.classroom.mapper.RedAskWaringMapper;
import com.redskt.classroom.service.RedAskWaringService;
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
public class RedAskWaringServiceImpl extends ServiceImpl<RedAskWaringMapper, RedAskWaring> implements RedAskWaringService {
    @Override
    public  int updateContentWarling(String wId, String uId,int type,String content) {
        return baseMapper.updateContentWarling(wId,uId,type,content);
    }
}
