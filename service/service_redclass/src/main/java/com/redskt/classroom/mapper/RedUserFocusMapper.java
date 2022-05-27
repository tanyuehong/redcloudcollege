package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedClassUser;
import com.redskt.classroom.entity.RedReplyGood;
import com.redskt.classroom.entity.RedUserFocus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-04-05
 */
public interface RedUserFocusMapper extends BaseMapper<RedUserFocus> {

    List<RedReplyGood> getUserFocusState(@Param("map") List<String> fIds, @Param("uid")  String uId);

    int updateUserFocusState(@Param("uid")  String uid, @Param("fid")  String fid);
}
