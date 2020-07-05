package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedClassUser;
import com.redskt.classroom.service.RedUserService;
import com.redskt.security.entity.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 自定义userDetailsService - 认证用户详情
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Service("userDetailsService")
public class RedUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RedUserService userService;

    /***
     * 根据账号获取用户信息
     * @param username:
     * @return: org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        RedClassUser user =  userService.selectByUsername(username);;

        // 判断用户是否存在
        if (null == user){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        // 返回UserDetails实现类
        com.redskt.security.entity.User curUser = new com.redskt.security.entity.User();
        BeanUtils.copyProperties(user,curUser);

        List<String> authorities = new ArrayList<>();
        authorities.add("ROLE_user");
        SecurityUser securityUser = new SecurityUser(curUser);
        securityUser.setPermissionValueList(authorities);
        return securityUser;
    }
}
