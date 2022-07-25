package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedBlogComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedCommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-04-21
 */
public interface RedBlogCommentMapper extends BaseMapper<RedBlogComment> {

    List<RedCommentVo> getRedBlogCommentList(@Param("bid") String bid, @Param("size") Integer size, @Param("rsize") Integer rsize, @Param("type") int type);

    RedCommentVo getBlogCommentOne(@Param("cid")  String cid);

    void addCommentGoodCount(@Param("cid")  String cid);

    void prepCommentGoodCount(@Param("cid")  String cid);

}
