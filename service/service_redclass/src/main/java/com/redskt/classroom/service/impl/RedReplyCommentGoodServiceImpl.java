package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedReplyCommentGood;
import com.redskt.classroom.mapper.RedReplyCommentGoodMapper;
import com.redskt.classroom.service.RedReplyCommentGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-03-17
 */
@Service
public class RedReplyCommentGoodServiceImpl extends ServiceImpl<RedReplyCommentGoodMapper, RedReplyCommentGood> implements RedReplyCommentGoodService {
    @Override
    public int updateReplyCommentGoodState(String uid,String cId) {
        return baseMapper.updateCommentGoodState(uid,cId);
    }
}
