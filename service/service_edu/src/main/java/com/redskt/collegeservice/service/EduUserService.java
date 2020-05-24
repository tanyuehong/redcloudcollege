package com.redskt.collegeservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.collegeservice.entity.EduUser;
import com.redskt.collegeservice.entity.frontvo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-16
 */
public interface EduUserService extends IService<EduUser> {

    String login(EduUser eduUser);

    void register(RegisterVo registerVo);
}
