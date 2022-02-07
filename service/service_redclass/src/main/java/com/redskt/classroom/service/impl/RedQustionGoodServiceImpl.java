package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedQustionGood;
import com.redskt.classroom.entity.vo.RedClassRegisterVo;
import com.redskt.classroom.mapper.RedQustionGoodMapper;
import com.redskt.classroom.service.RedQustionGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
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
public class RedQustionGoodServiceImpl extends ServiceImpl<RedQustionGoodMapper, RedQustionGood> implements RedQustionGoodService {
    @Override
    public int updateGoodState(String uid,String bid) {
        return baseMapper.updateGoodState(uid,bid);
    }
}
