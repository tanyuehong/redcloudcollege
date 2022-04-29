package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedBlogCommentReply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedBlogCommentReplyVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-04-26
 */
public interface RedBlogCommentReplyMapper extends BaseMapper<RedBlogCommentReply> {

    RedBlogCommentReplyVo getBlogCommentReplyOne(@Param("rid")  String rid);

    void addCommentReplyGoodCount(@Param("cid")  String cid);

    void prepCommentReplyGoodCount(@Param("cid")  String cid);

}
