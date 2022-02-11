package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedReplyGood;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-02-11
 */
public interface RedReplyGoodMapper extends BaseMapper<RedReplyGood> {
    int updateGoodState(@Param("uid")  String uid, @Param("rid")  String rId);

    int getUserReply(Map<String, Object> map, @Param("uid")  String uId);
}
