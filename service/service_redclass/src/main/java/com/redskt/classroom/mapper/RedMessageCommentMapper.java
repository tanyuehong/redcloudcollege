package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedMessageComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedMessageCommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-05-12
 */
public interface RedMessageCommentMapper extends BaseMapper<RedMessageComment> {

    List<RedMessageCommentVo> getMessageCommentList(@Param("mid") String bid, @Param("size") Integer size, @Param("rsize") Integer rsize, @Param("type") int type);

    RedMessageCommentVo getMessageCommentOne(@Param("mid")  String mid);

    void addCommentGoodCount(@Param("cid")  String cid);

    void prepCommentGoodCount(@Param("cid")  String cid);

}
