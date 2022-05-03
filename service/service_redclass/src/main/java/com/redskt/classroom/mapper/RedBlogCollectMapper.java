package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedBlogCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-04-30
 */
public interface RedBlogCollectMapper extends BaseMapper<RedBlogCollect> {

    int updateBlogCollectState(@Param("bid") String bid, @Param("uid") String uid);

}
