package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedInterviewCommentReplyGood;
import com.redskt.classroom.mapper.RedInterviewCommentReplyGoodMapper;
import com.redskt.classroom.service.RedInterviewCommentReplyGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-08-25
 */
@Service
public class RedInterviewCommentReplyGoodServiceImpl extends ServiceImpl<RedInterviewCommentReplyGoodMapper, RedInterviewCommentReplyGood> implements RedInterviewCommentReplyGoodService {

    @Override
    public int updateGoodState(String uid, String cId, int type) {
        return baseMapper.updateGoodState(uid, cId, type);
    }
}
