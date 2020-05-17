package com.redskt.ucenter.service;

import com.redskt.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-16
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVo registerVo);
}
