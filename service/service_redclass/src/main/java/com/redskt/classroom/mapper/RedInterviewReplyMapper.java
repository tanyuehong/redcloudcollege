package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedInterviewReply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-21
 */
public interface RedInterviewReplyMapper extends BaseMapper<RedInterviewReply> {

    List<RedInterviewReply> getInterViewReply(@Param("qid")  String qId);
}
