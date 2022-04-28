package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedBlogCommentGood;
import com.redskt.classroom.mapper.RedBlogCommentGoodMapper;
import com.redskt.classroom.service.RedBlogCommentGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-04-28
 */
@Service
public class RedBlogCommentGoodServiceImpl extends ServiceImpl<RedBlogCommentGoodMapper, RedBlogCommentGood> implements RedBlogCommentGoodService {

    @Override
    public int updateCommentGoodState(String uid, String cId, int type) {
        return baseMapper.updateCommentGoodState(uid,cId,type);
    }
}
