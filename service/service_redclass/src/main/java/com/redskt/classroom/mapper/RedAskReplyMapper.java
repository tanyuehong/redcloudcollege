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

    RedAskReplyVo getUserLasterReply(@Param("uid")  String uid);

    List<RedAskReplyVo> getQustionReplyList(@Param("qId")  String qId,@Param("size") Integer size,@Param("csize") Integer csize,@Param("type") Integer type);

    int addReplyGoodCount(@Param("rid") String rId);

    int prepReplyGoodCount(@Param("rid") String rId);

    int addReplyBadCount(@Param("rid") String rId);

    int prepReplyBadCount(@Param("rid") String rId);

    int updateReplyState(@Param("rid") String rId,@Param("state") int state);
}
