package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedInterviewAnswerCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-08-16
 */
public interface RedInterviewAnswerCollectMapper extends BaseMapper<RedInterviewAnswerCollect> {

    int updateCollectState(@Param("uid")  String uid, @Param("aid")  String aId);

}
