package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedBlogComment;
import com.redskt.classroom.entity.vo.RedCommentVo;
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
    public List<RedCommentVo> getRedBlogCommentList(String bid, Integer rsize, int type) {
        if (type == 1) {
            return  baseMapper.getRedBlogCommentList(bid,5,5,1);
        } else {
            return  baseMapper.getRedBlogCommentList(bid,5,5,2);
        }
    }

    @Override
    public RedCommentVo getBlogCommentOne(String cid) {
        return baseMapper.getBlogCommentOne(cid);
    }

    @Override
    public void addCommentGoodCount(String cid) {
        baseMapper.addCommentGoodCount(cid);
    }

    @Override
    public void prepCommentGoodCount(String cid) {
        baseMapper.prepCommentGoodCount(cid);
    }
}
