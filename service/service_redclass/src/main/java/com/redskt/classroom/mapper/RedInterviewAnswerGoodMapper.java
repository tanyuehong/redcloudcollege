package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedInterviewAnswerGood;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-08-12
 */
public interface RedInterviewAnswerGoodMapper extends BaseMapper<RedInterviewAnswerGood> {
    int updateAnswerGoodState(@Param("uid")  String uid, @Param("aid")  String aid);
}
