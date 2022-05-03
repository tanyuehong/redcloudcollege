package com.redskt.classroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.RedClassUser;
import com.redskt.classroom.entity.vo.RedClassUserVo;
import com.redskt.classroom.entity.vo.RedUserAskVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-16
 */
public interface RedUserMapper extends BaseMapper<RedClassUser> {

    Integer countRegisterDay(String day);

    RedUserAskVo getUserAskInfo(@Param("uid") String uid);

    RedClassUserVo getUserInfoFocusCount(@Param("uid") String uid);

    int updateUserInfo(Map<String, Object> map,@Param("id") String userId);

    int changeUserPwd(@Param("map") Map<String, Object> parms, @Param("oldPwd") String oldPwd,@Param("id") String userId);

}
