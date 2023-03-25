package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedCategoryTag;
import com.redskt.classroom.entity.RedInterviewPosition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-12
 */
public interface RedInterviewPositionMapper extends BaseMapper<RedInterviewPosition> {
    List<RedCategoryTag> getInterviewPositionTagList(@Param("pId") String pId);
}
