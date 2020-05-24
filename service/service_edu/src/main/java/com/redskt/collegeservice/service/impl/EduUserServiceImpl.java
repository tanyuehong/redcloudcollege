package com.redskt.collegeservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redskt.collegeservice.entity.EduUser;
import com.redskt.collegeservice.entity.frontvo.RegisterVo;
import com.redskt.collegeservice.mapper.EduUserMapper;
import com.redskt.collegeservice.service.EduUserService;
import com.redskt.commonutils.JwtUtils;
import com.redskt.servicebase.excepionhandler.RedCloudCollegeExceptionHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.redskt.collegeservice.utils.MD5;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-16
 */
@Service
public class EduUserServiceImpl extends ServiceImpl<EduUserMapper, EduUser> implements EduUserService {
    //登录的方法
    @Override
    public String login(EduUser eduUser) {
        //获取登录手机号和密码
        String mobile = eduUser.getMobile();
        String password = eduUser.getPassword();

        //手机号和密码非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new RedCloudCollegeExceptionHandler(20001,"登录失败");
        }

        //判断手机号是否正确
        QueryWrapper<EduUser> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        EduUser mobileMember = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if(mobileMember == null) {//没有这个手机号
            throw new RedCloudCollegeExceptionHandler(20001,"登录失败");
        }

        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式 MD5
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new RedCloudCollegeExceptionHandler(20001,"登录失败");
        }

        //判断用户是否禁用
        if(mobileMember.getIsDisabled()) {
            throw new RedCloudCollegeExceptionHandler(20001,"登录失败");
        }

        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    //注册的方法
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getMobile(); //手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码

        //非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
               || StringUtils.isEmpty(nickname)) {
            throw new RedCloudCollegeExceptionHandler(20001,"注册失败");
        }
        //判断验证码
        //获取redis验证码
//        String redisCode = redisTemplate.opsForValue().get(mobile);
//        if(!code.equals(redisCode)) {
//            throw new RedCloudCollegeExceptionHandler(20001,"注册失败");
//        }

        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<EduUser> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new RedCloudCollegeExceptionHandler(20001,"注册失败");
        }

        //数据添加数据库中
        EduUser member = new EduUser();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//密码需要加密的
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }
}
