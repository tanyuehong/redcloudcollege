package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedReplyCommentGood;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-03-17
 */
public interface RedReplyCommentGoodMapper extends BaseMapper<RedReplyCommentGood> {

    int updateCommentGoodState(@Param("uid")  String uid, @Param("cid")  String cId);
}
