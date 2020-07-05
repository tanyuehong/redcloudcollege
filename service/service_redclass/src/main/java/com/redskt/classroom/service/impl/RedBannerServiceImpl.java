package com.redskt.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redskt.classroom.entity.RedClassBanner;
import com.redskt.classroom.mapper.RedBannerMapper;
import com.redskt.classroom.service.RedBannerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-14
 */
@Service
public class RedBannerServiceImpl extends ServiceImpl<RedBannerMapper, RedClassBanner> implements RedBannerService {

    @Override
    public List<RedClassBanner> selectAllBanner() {
        //根据id进行降序排列，显示排列之后前两条记录
        QueryWrapper<RedClassBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //last方法，拼接sql语句
        wrapper.last("limit 2");
        List<RedClassBanner> list = baseMapper.selectList(null);
        return list;
    }
}
