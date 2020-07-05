package com.redskt.classroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.RedClassUser;

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
}
