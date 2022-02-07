package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedQustionGood;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-02-07
 */
public interface RedQustionGoodMapper extends BaseMapper<RedQustionGood> {
    int updateGoodState(@Param("uid")  String uid, @Param("bid")  String bid);
}
