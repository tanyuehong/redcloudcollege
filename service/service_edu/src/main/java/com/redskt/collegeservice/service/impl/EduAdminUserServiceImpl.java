package com.redskt.collegeservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redskt.collegeservice.entity.admin.EduAdminUser;
import com.redskt.collegeservice.mapper.EduAdminUserMapper;
import com.redskt.collegeservice.service.EduAdminUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class EduAdminUserServiceImpl extends ServiceImpl<EduAdminUserMapper, EduAdminUser> implements EduAdminUserService {

    @Override
    public EduAdminUser selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<EduAdminUser>().eq("username", username));
    }
}
