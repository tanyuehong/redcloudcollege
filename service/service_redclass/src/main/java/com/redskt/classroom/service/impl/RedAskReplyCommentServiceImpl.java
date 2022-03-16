package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedAskReplyComment;
import com.redskt.classroom.entity.vo.ReplyCommentVo;
import com.redskt.classroom.mapper.RedAskReplyCommentMapper;
import com.redskt.classroom.service.RedAskReplyCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-01-21
 */
@Service
public class RedAskReplyCommentServiceImpl extends ServiceImpl<RedAskReplyCommentMapper, RedAskReplyComment> implements RedAskReplyCommentService {
    @Override
    public ReplyCommentVo getUerCommentOne(String uId) {
        return  baseMapper.getUerCommentOne(uId);
    }
}
