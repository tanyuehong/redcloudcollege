package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedAskReplyComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.ReplyCommentVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-01-21
 */
public interface RedAskReplyCommentMapper extends BaseMapper<RedAskReplyComment> {
    ReplyCommentVo getUerCommentOne(@Param("uid")  String uId);

    int addReplyCommentGoodCount(@Param("cid")  String cId);

    int prepReplyCommentGoodCount(@Param("cid")  String cId);
}
