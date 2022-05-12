package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedMessageComment;
import com.redskt.classroom.entity.vo.RedMessageCommentVo;
import com.redskt.classroom.mapper.RedMessageCommentMapper;
import com.redskt.classroom.service.RedMessageCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-05-12
 */
@Service
public class RedMessageCommentServiceImpl extends ServiceImpl<RedMessageCommentMapper, RedMessageComment> implements RedMessageCommentService {

    @Override
    public List<RedMessageCommentVo> getMessageCommentList(String bid, Integer size, Integer rsize, int type) {
        return baseMapper.getMessageCommentList(bid,size,rsize,type);
    }

    @Override
    public RedMessageCommentVo getMessageCommentOne(String mid) {
        return baseMapper.getMessageCommentOne(mid);
    }

    @Override
    public void addCommentGoodCount(String mid) {
        baseMapper.addCommentGoodCount(mid);
    }

    @Override
    public void prepCommentGoodCount(String mid) {
        baseMapper.prepCommentGoodCount(mid);
    }
}
