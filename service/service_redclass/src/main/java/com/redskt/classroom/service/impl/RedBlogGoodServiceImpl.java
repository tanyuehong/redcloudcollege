package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedBlogGood;
import com.redskt.classroom.mapper.RedBlogGoodMapper;
import com.redskt.classroom.service.RedBlogGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-02-07
 */
@Service
public class RedBlogGoodServiceImpl extends ServiceImpl<RedBlogGoodMapper, RedBlogGood> implements RedBlogGoodService {
    @Override
    public int updateGoodState(String uid,String bid) {
        return baseMapper.updateGoodState(uid,bid);
    }
}
