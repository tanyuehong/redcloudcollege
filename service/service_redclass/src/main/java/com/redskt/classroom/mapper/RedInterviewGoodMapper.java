package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedInterviewGood;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-08-10
 */
public interface RedInterviewGoodMapper extends BaseMapper<RedInterviewGood> {

    int updateGoodState(@Param("uid")  String uid, @Param("qid")  String qId);

}
