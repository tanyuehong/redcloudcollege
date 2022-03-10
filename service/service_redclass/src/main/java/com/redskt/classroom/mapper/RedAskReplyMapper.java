package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedAskReply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedAskReplyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-01-21
 */
public interface RedAskReplyMapper extends BaseMapper<RedAskReply> {

    List<RedAskReplyVo> getQustionReplyList(@Param("qId")  String qId,@Param("size") Integer size,@Param("csize") Integer csize,@Param("type") Integer type);

    int addReplyGoodCount(@Param("rid")  String qId);

    int prepReplyGoodCount(@Param("rid")  String qId);

    int addReplyBadCount(@Param("rid")  String qId);

    int prepReplyBadCount(@Param("rid")  String qId);
}
