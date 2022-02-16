package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedReplyBad;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-02-15
 */
public interface RedReplyBadMapper extends BaseMapper<RedReplyBad> {
    int updateBadState(@Param("uid")  String uid, @Param("rid")  String rId);

    int getUserReplyBadState(Map<String, Object> map, @Param("uid")  String uId);

}
