package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedBlogCollect;
import com.redskt.classroom.mapper.RedBlogCollectMapper;
import com.redskt.classroom.service.RedBlogCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-04-30
 */
@Service
public class RedBlogCollectServiceImpl extends ServiceImpl<RedBlogCollectMapper, RedBlogCollect> implements RedBlogCollectService {

    @Override
    public int updateBlogCollectState(String bid,String uid) {
        return baseMapper.updateBlogCollectState(bid,uid);
    }
}
