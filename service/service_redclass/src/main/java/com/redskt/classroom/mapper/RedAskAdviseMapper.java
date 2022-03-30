package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedAskAdvise;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-03-25
 */
public interface RedAskAdviseMapper extends BaseMapper<RedAskAdvise> {
    int updateQustionAdvise(@Param("qid")  String wId,@Param("uid")  String uId,@Param("type")  int type,@Param("content")  String content);
}
