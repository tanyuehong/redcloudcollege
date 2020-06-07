package com.redskt.collegeservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.collegeservice.entity.admin.EduAdminUserRole;
import com.redskt.collegeservice.entity.admin.EduRole;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface EduAdminRoleService extends IService<EduRole> {

    //根据用户获取角色数据
    Map<String, Object> findRoleByUserId(String userId);

    //根据用户分配角色
    void saveUserRoleRealtionShip(String userId, String[] roleId);

    List<EduRole> selectRoleByUserId(String id);
}
