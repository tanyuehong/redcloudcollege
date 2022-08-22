package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedInterviewCommentGood;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-25
 */
public interface RedInterviewCommentGoodMapper extends BaseMapper<RedInterviewCommentGood> {

    int updateCommentGoodState(@Param("uid") String uid, @Param("cid") String cId, @Param("type") int type);
}
