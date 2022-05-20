package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedMessage;
import com.redskt.classroom.entity.vo.RedClassBlogDetailVo;
import com.redskt.classroom.entity.vo.RedMessageDtailVo;
import com.redskt.classroom.entity.vo.RedUserStateVo;
import com.redskt.classroom.mapper.RedMessageMapper;
import com.redskt.classroom.service.RedMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-05-10
 */
@Service
public class RedMessageServiceImpl extends ServiceImpl<RedMessageMapper, RedMessage> implements RedMessageService {

    @Override
    public List<RedClassBlogDetailVo> getRedmessageDetailList(int size, int type, String uid) {
        return baseMapper.getRedmessageDetailList(size,type,uid);
    }

    @Override
    public RedMessageDtailVo getRedMessageDetail(String mId) {
        return baseMapper.getRedMessageDetail(mId);
    }

    @Override
    public int updateReadCount(String mid) {
        return baseMapper.updateReadCount(mid);
    }

    @Override
    public int updateGoodCount(boolean isAdd,String mid) {
        if(isAdd) {
            return baseMapper.addGoodCount(mid);
        } else {
            return baseMapper.prepGoodCount(mid);
        }
    }

    @Override
    public RedUserStateVo getUserStatus(String mid, String uid) {
        return baseMapper.getUserStatus(mid,uid);
    }
}
