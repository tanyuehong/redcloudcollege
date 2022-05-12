package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedMessageGood;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-05-12
 */
public interface RedMessageGoodMapper extends BaseMapper<RedMessageGood> {

    int updateGoodState(@Param("uid")  String uid, @Param("mid")  String mid);

}
