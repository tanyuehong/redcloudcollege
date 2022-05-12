package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedMessageDtailVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-05-10
 */
public interface RedMessageMapper extends BaseMapper<RedMessage> {

    RedMessageDtailVo getRedMessageDetail(@Param("mid") String mId);

    int updateReadCount(@Param("mid") String mid);

}
