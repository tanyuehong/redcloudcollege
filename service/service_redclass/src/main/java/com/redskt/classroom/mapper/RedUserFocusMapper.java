package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedUserFocus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-04-05
 */
public interface RedUserFocusMapper extends BaseMapper<RedUserFocus> {
    int updateUserFocusState(@Param("uid")  String uid, @Param("fid")  String fid);
}
