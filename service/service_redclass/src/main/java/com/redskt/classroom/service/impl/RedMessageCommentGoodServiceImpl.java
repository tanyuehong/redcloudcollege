package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedMessageCommentGood;
import com.redskt.classroom.mapper.RedMessageCommentGoodMapper;
import com.redskt.classroom.service.RedMessageCommentGoodService;
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
public class RedMessageCommentGoodServiceImpl extends ServiceImpl<RedMessageCommentGoodMapper, RedMessageCommentGood> implements RedMessageCommentGoodService {
    @Override
    public int updateCommentGoodState(String uid, String cId, int type) {
        return baseMapper.updateCommentGoodState(uid,cId,type);
    }
}
