package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedInterviewCommentReply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedCommentReplyVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-25
 */
public interface RedInterviewCommentReplyMapper extends BaseMapper<RedInterviewCommentReply> {

    RedCommentReplyVo getCommentReplyOne(@Param("rid")  String rId);

    int addGoodCount(@Param("cId") String cId);

    int prepGoodCount(@Param("cId") String cId);
}
