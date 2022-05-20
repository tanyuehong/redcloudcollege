package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedClassBlogDetailVo;
import com.redskt.classroom.entity.vo.RedMessageDtailVo;
import com.redskt.classroom.entity.vo.RedUserStateVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-05-10
 */
public interface RedMessageMapper extends BaseMapper<RedMessage> {

    List<RedClassBlogDetailVo> getRedmessageDetailList(@Param("size") int size, @Param("type") int type, @Param("uid") String uid);

    RedMessageDtailVo getRedMessageDetail(@Param("mid") String mId);

    int updateReadCount(@Param("mid") String mid);

    int addGoodCount(@Param("mid") String mid);

    int prepGoodCount(@Param("mid") String mid);

    RedUserStateVo getUserStatus(@Param("mid") String mid, @Param("uid") String uid);

}
