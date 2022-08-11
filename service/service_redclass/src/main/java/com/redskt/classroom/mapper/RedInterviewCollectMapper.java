package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedInterviewCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-08-11
 */
public interface RedInterviewCollectMapper extends BaseMapper<RedInterviewCollect> {
    int updateCollectState(@Param("uid")  String uid, @Param("qid")  String qId);
}
