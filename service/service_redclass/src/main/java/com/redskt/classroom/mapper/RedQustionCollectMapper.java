package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedQustionCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-02-09
 */
public interface RedQustionCollectMapper extends BaseMapper<RedQustionCollect> {
    int updateCollectState(@Param("uid")  String uid, @Param("qid")  String qId);
}
