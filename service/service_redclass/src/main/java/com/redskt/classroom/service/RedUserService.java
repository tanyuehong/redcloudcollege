package com.redskt.classroom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.RedClassUser;
import com.redskt.classroom.entity.vo.RedClassRegisterVo;
import com.redskt.classroom.entity.vo.RedClassUserVo;
import com.redskt.classroom.entity.vo.RedUserAskVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-16
 */
public interface RedUserService extends IService<RedClassUser> {


    boolean checkIsAdmin(String uId);

    List<RedClassUserVo> getFocusUserList(String uId);

    List<RedClassUserVo> getFansUserList(String uId);

    int register(RedClassRegisterVo registerVo);

    RedClassUserVo getShowUserInfo(String uId);

    RedUserAskVo getAskUserInfo(String uId);

    RedClassUserVo getUserInfoFocusCount(String uId);
    // 从数据库中取出用户信息
    RedClassUser selectByUsername(String username);

    int updateUserInfo(Map<String, Object> map,String uId);

    int changeUserPwd(Map<String, Object> map,String oldPwd,String uId);
}
