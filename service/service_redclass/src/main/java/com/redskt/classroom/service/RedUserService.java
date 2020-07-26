package com.redskt.classroom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.RedClassUser;
import com.redskt.classroom.entity.vo.RedClassRegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-16
 */
public interface RedUserService extends IService<RedClassUser> {

    void register(RedClassRegisterVo registerVo);

    // 从数据库中取出用户信息
    RedClassUser selectByUsername(String username);

}
