package com.redskt.collegeservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.collegeservice.entity.EduBanner;
import com.redskt.collegeservice.mapper.CrmBannerMapper;
import com.redskt.collegeservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, EduBanner> implements CrmBannerService {

    @Override
    public List<EduBanner> selectAllBanner() {
        //根据id进行降序排列，显示排列之后前两条记录
        QueryWrapper<EduBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //last方法，拼接sql语句
        wrapper.last("limit 2");
        List<EduBanner> list = baseMapper.selectList(null);
        return list;
    }
}
