package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedBlogGood;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-02-07
 */
public interface RedBlogGoodMapper extends BaseMapper<RedBlogGood> {
    int updateGoodState(@Param("uid")  String uid,@Param("bid")  String bid);
}
