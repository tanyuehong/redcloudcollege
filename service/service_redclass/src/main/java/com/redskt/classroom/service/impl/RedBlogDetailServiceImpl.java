package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.OpBlogDetail;
import com.redskt.classroom.entity.vo.RedUserStateVo;
import com.redskt.classroom.entity.vo.RedClassBlogDetailVo;
import com.redskt.classroom.mapper.OpBlogDetailMapper;
import com.redskt.classroom.service.RedBlogDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2021-11-17
 */
@Service
public class RedBlogDetailServiceImpl extends ServiceImpl<OpBlogDetailMapper, OpBlogDetail> implements RedBlogDetailService {

    @Override
    public RedClassBlogDetailVo getRedClassBlogDetail(String bid) {
        return baseMapper.getRedClassBlogDetail(bid);
    }

    @Override
    public List<RedClassBlogDetailVo> getRedBlogDetailList(int size,int type) {
        return baseMapper.getRedBlogDetailList(size,type);
    }

    @Override
    public RedUserStateVo getBlogUserStatus(String bid, String uid) {
        return baseMapper.getBlogUserStatus(bid,uid);
    }

    @Override
    public int updateBlogGoodCount(boolean isAdd,String bid) {
        if(isAdd) {
            return baseMapper.addBlogGoodCount(bid);
        } else {
            return baseMapper.prepBlogGoodCount(bid);
        }
    }

    @Override
    public int updateReadCount(String bid) {
        return baseMapper.updateReadCount(bid);
    }
}
