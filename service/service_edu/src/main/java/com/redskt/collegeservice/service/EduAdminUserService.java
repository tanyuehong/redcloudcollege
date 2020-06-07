package com.redskt.collegeservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.collegeservice.entity.admin.EduAdminUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface EduAdminUserService extends IService<EduAdminUser> {

    // 从数据库中取出用户信息
    EduAdminUser selectByUsername(String username);
}
