package com.redskt.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redskt.classroom.entity.RedClassUser;
import com.redskt.classroom.entity.vo.RedClassRegisterVo;
import com.redskt.classroom.entity.vo.RedClassUserVo;
import com.redskt.classroom.entity.vo.RedUserAskVo;
import com.redskt.classroom.mapper.RedUserMapper;
import com.redskt.classroom.service.RedUserService;
import com.redskt.commonutils.MD5;
import com.redskt.commonutils.JwtUtils;
import com.redskt.servicebase.excepionhandler.RedCloudCollegeExceptionHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-16
 */
@Service
public class RedUserServiceImpl extends ServiceImpl<RedUserMapper, RedClassUser> implements RedUserService {

    @Override
    public boolean checkIsAdmin(String uId) {
        QueryWrapper<RedClassUser> wrapper = new QueryWrapper<>();
        wrapper.select("id","username","authority");
        wrapper.eq("id",uId);
        RedClassUser eduUser = this.getOne(wrapper);
        if (eduUser!=null && eduUser.getAuthority() == 100) {
            return true;
        }
        return false;
    }

    @Override
    public List<RedClassUserVo> getFocusUserList(String uId) {
        return baseMapper.getFocusUserList(uId);
    }

    @Override
    public List<RedClassUserVo> getFansUserList(String uId) {
        return baseMapper.getFansUserList(uId);
    }

    @Override
    public RedUserAskVo getAskUserInfo(String uId) {
        return baseMapper.getUserAskInfo(uId);
    }

    @Override
    public RedClassUserVo getShowUserInfo(String uId) {
        return baseMapper.getShowUserInfo(uId);
    }

    @Override
    public RedClassUserVo getUserInfoFocusCount(String uId) {
        return baseMapper.getUserInfoFocusCount(uId);
    }
    //注册的方法
    @Override
    public int register(RedClassRegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode(); //验证码
        String username = registerVo.getMobile(); //手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码

        //非空判断
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)
               || StringUtils.isEmpty(nickname)) {
            return 4;
        }
        //判断验证码
        //获取redis验证码
//        String redisCode = redisTemplate.opsForValue().get(mobile);
//        if(!code.equals(redisCode)) {
//            throw new RedCloudCollegeExceptionHandler(20001,"注册失败");
//        }

        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<RedClassUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            return 5;
        }

        //数据添加数据库中
        RedClassUser member = new RedClassUser();
        member.setUsername(username);
        member.setNickname(nickname);
        member.setSex(1);
        member.setPassword(MD5.encrypt(password));//密码需要加密的
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("https://static.redskt.com/assets/img/yonghutouxiangnan.png");
        return baseMapper.insert(member) ==1?1:6;
    }

    @Override
    public RedClassUser selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<RedClassUser>().eq("username", username));
    }

    @Override
    public int updateUserInfo(Map<String, Object> map,String uId) {
        return  baseMapper.updateUserInfo(map,uId);
    }

    @Override
    public int changeUserPwd(Map<String, Object> map,String oldPwd,String uId) {
        return  baseMapper.changeUserPwd(map,oldPwd,uId);
    }
}
