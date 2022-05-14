package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedMessageCommentReply;
import com.redskt.classroom.entity.vo.RedMessageReplyVo;
import com.redskt.classroom.mapper.RedMessageCommentReplyMapper;
import com.redskt.classroom.service.RedMessageCommentReplyService;
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
public class RedMessageCommentReplyServiceImpl extends ServiceImpl<RedMessageCommentReplyMapper, RedMessageCommentReply> implements RedMessageCommentReplyService {

    @Override
    public RedMessageReplyVo getMessageCommentReplyOne(String rid) {
        return baseMapper.getMessageCommentReplyOne(rid);
    }

    @Override
    public void addReplyGoodCount(String mid) {
        baseMapper.addReplyGoodCount(mid);
    }

    @Override
    public void prepReplyGoodCount(String mid) {
        baseMapper.prepReplyGoodCount(mid);
    }
}
