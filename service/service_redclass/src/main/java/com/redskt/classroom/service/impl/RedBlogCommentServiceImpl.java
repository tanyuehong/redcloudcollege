package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedBlogComment;
import com.redskt.classroom.entity.vo.RedBlogCommentVo;
import com.redskt.classroom.mapper.RedBlogCommentMapper;
import com.redskt.classroom.service.RedBlogCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-04-21
 */
@Service
public class RedBlogCommentServiceImpl extends ServiceImpl<RedBlogCommentMapper, RedBlogComment> implements RedBlogCommentService {

    @Override
    public List<RedBlogCommentVo> getRedBlogCommentList(String bid,Integer rsize,int type) {
        return baseMapper.getRedBlogCommentList(bid,rsize,type);
    }

    @Override
    public RedBlogCommentVo getBlogCommentOne(String cid) {
        return baseMapper.getBlogCommentOne(cid);
    }
}
