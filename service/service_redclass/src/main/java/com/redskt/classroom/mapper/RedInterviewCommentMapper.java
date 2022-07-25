package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedInterviewComment;
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
 * @since 2022-07-25
 */
public interface RedInterviewCommentMapper extends BaseMapper<RedInterviewComment> {

    List<RedCommentVo> getRedCommentList(@Param("id") String id, @Param("size") Integer size, @Param("rsize") Integer rsize, @Param("type") int type);

}
