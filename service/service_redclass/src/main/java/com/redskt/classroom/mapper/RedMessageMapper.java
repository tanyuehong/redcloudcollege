package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedMessageDtailVo;
import com.redskt.classroom.entity.vo.RedUserStateVo;
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

    int addGoodCount(@Param("mid") String mid);

    int prepGoodCount(@Param("mid") String mid);

    RedUserStateVo getUserStatus(@Param("mid") String mid, @Param("uid") String uid);

}
