package com.redskt.collegeservice.service.impl;

import com.redskt.collegeservice.entity.admin.EduRole;
import com.redskt.collegeservice.entity.admin.EduAdminUserRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redskt.collegeservice.mapper.EduAdminUserRoleMapper;
import com.redskt.collegeservice.mapper.EduRoleMapper;
import com.redskt.collegeservice.service.EduAdminRoleService;
import com.redskt.collegeservice.service.EduAdminUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class EduAdminRoleServiceImpl extends ServiceImpl<EduRoleMapper, EduRole> implements EduAdminRoleService {

    @Autowired
    private EduAdminUserRoleService userRoleService;


    //根据用户获取角色数据
    @Override
    public Map<String, Object> findRoleByUserId(String userId) {
        //查询所有的角色
        List<EduRole> allRolesList =baseMapper.selectList(null);

        //根据用户id，查询用户拥有的角色id
        List<EduAdminUserRole> existUserRoleList = userRoleService.list(new QueryWrapper<EduAdminUserRole>().eq("user_id", userId).select("role_id"));

        List<String> existRoleList = existUserRoleList.stream().map(c->c.getRoleId()).collect(Collectors.toList());

        //对角色进行分类
        List<EduRole> assignRoles = new ArrayList<EduRole>();
        for (EduRole role : allRolesList) {
            //已分配
            if(existRoleList.contains(role.getId())) {
                assignRoles.add(role);
            }
        }

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignRoles", assignRoles);
        roleMap.put("allRolesList", allRolesList);
        return roleMap;
    }

    //根据用户分配角色
    @Override
    public void saveUserRoleRealtionShip(String userId, String[] roleIds) {
        userRoleService.remove(new QueryWrapper<EduAdminUserRole>().eq("user_id", userId));

        List<EduAdminUserRole> userRoleList = new ArrayList<>();
        for(String roleId : roleIds) {
            if(StringUtils.isEmpty(roleId)) continue;
            EduAdminUserRole userRole = new EduAdminUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);

            userRoleList.add(userRole);
        }
        userRoleService.saveBatch(userRoleList);
    }

    @Override
    public List<EduRole> selectRoleByUserId(String id) {
        //根据用户id拥有的角色id
        List<EduAdminUserRole> userRoleList = userRoleService.list(new QueryWrapper<EduAdminUserRole>().eq("user_id", id).select("role_id"));
        List<String> roleIdList = userRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        List<EduRole> roleList = new ArrayList<>();
        if(roleIdList.size() > 0) {
            roleList = baseMapper.selectBatchIds(roleIdList);
        }
        return roleList;
    }
}
