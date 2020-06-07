package com.redskt.collegeservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.collegeservice.entity.admin.EduPermission;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface EduPermissionMapper extends BaseMapper<EduPermission> {


    List<String> selectPermissionValueByUserId(String id);

    List<String> selectAllPermissionValue();

    List<EduPermission> selectPermissionByUserId(String userId);
}
