package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedInterviewAnswer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedClassAnswerVo;
import com.redskt.classroom.entity.vo.RedClassReplyVo;
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
public interface RedInterviewAnswerMapper extends BaseMapper<RedInterviewAnswer> {

    List<RedClassAnswerVo> getInterviewAnswerList(@Param("qId")  String qId, @Param("size") Integer size, @Param("csize") Integer csize, @Param("type") Integer type);

    RedClassAnswerVo getUserLasterReply(@Param("qid")  String qid);
}
