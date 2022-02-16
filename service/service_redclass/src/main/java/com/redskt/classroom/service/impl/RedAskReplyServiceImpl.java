package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedAskReply;
import com.redskt.classroom.entity.vo.RedAskReplyVo;
import com.redskt.classroom.mapper.RedAskReplyMapper;
import com.redskt.classroom.service.RedAskReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-01-21
 */
@Service
public class RedAskReplyServiceImpl extends ServiceImpl<RedAskReplyMapper, RedAskReply> implements RedAskReplyService {

    @Override
    public List<RedAskReplyVo> getHomeAskReplyList(String qId) {
        return baseMapper.getQustionReplyList(qId);
    }

    @Override
    public int updateReplyGoodCount(boolean isAdd,String rId) {
        if(isAdd) {
            return baseMapper.addReplyGoodCount(rId);
        } else {
            return baseMapper.prepReplyGoodCount(rId);
        }
    }

    @Override
    public int updateReplyBadCount(boolean isAdd,String rId) {
        if(isAdd) {
            return baseMapper.addReplyBadCount(rId);
        } else {
            return baseMapper.prepReplyBadCount(rId);
        }
    }
}
