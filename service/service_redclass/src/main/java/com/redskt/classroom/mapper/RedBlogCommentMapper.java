package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedBlogComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedBlogCommentVo;
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

    List<RedBlogCommentVo> getRedBlogCommentList(@Param("bid")  String bid,@Param("size")  Integer rsize,@Param("type")  int type);

}
