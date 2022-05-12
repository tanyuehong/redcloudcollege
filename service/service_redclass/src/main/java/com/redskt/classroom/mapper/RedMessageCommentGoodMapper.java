package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedMessageCommentGood;
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
public interface RedMessageCommentGoodMapper extends BaseMapper<RedMessageCommentGood> {

    int updateCommentGoodState(@Param("uid") String uid, @Param("cid") String cId, @Param("type") int type);

}
