package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedMessageCommentReply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedMessageReplyVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-05-12
 */
public interface RedMessageCommentReplyMapper extends BaseMapper<RedMessageCommentReply> {

    RedMessageReplyVo getMessageCommentReplyOne(@Param("rid")  String mid);

    void addReplyGoodCount(@Param("rid")  String rid);

    void prepReplyGoodCount(@Param("rid")  String rid);
}
