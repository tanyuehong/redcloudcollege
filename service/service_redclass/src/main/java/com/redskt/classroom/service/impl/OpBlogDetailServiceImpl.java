package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.OpBlogDetail;
import com.redskt.classroom.mapper.OpBlogDetailMapper;
import com.redskt.classroom.service.OpBlogDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2021-11-17
 */
@Service
public class OpBlogDetailServiceImpl extends ServiceImpl<OpBlogDetailMapper, OpBlogDetail> implements OpBlogDetailService {
    @Override
    public int updateBlogGoodCount(boolean isAdd,String bid) {
        if(isAdd) {
            return baseMapper.addBlogGoodCount(bid);
        } else {
            return baseMapper.prepBlogGoodCount(bid);
        }
    }
}
