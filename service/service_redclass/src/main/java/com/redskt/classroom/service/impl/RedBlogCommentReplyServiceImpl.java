package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedBlogCommentReply;
import com.redskt.classroom.entity.vo.RedBlogCommentReplyVo;
import com.redskt.classroom.mapper.RedBlogCommentReplyMapper;
import com.redskt.classroom.service.RedBlogCommentReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-04-26
 */
@Service
public class RedBlogCommentReplyServiceImpl extends ServiceImpl<RedBlogCommentReplyMapper, RedBlogCommentReply> implements RedBlogCommentReplyService {

    @Override
    public RedBlogCommentReplyVo getBlogCommentReplyOne(String rid) {
        return baseMapper.getBlogCommentReplyOne(rid);
    }

    @Override
    public void addCommentReplyGoodCount(String cid) {
        baseMapper.addCommentReplyGoodCount(cid);
    }

    @Override
    public void prepCommentReplyGoodCount(String cid) {
        baseMapper.prepCommentReplyGoodCount(cid);
    }
}
