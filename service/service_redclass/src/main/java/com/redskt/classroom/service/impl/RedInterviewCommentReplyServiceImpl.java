package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedInterviewCommentReply;
import com.redskt.classroom.entity.vo.RedCommentReplyVo;
import com.redskt.classroom.mapper.RedInterviewCommentReplyMapper;
import com.redskt.classroom.service.RedInterviewCommentReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-25
 */
@Service
public class RedInterviewCommentReplyServiceImpl extends ServiceImpl<RedInterviewCommentReplyMapper, RedInterviewCommentReply> implements RedInterviewCommentReplyService {

    @Override
    public RedCommentReplyVo getCommentReplyOne(String rId) {
        return baseMapper.getCommentReplyOne(rId);
    }
}
