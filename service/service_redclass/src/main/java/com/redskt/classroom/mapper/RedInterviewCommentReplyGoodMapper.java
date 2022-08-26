package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedInterviewCommentReplyGood;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-08-25
 */
public interface RedInterviewCommentReplyGoodMapper extends BaseMapper<RedInterviewCommentReplyGood> {

    int updateGoodState(@Param("uid") String uid, @Param("cid") String cId, @Param("type") int type);
}
