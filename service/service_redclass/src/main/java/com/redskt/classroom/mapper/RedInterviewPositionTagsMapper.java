package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedCategoryTag;
import com.redskt.classroom.entity.RedInterviewPositionTags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.admin.vo.RedInterviewPositionTagsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2023-04-09
 */
public interface RedInterviewPositionTagsMapper extends BaseMapper<RedInterviewPositionTags> {

    List<RedInterviewPositionTagsVo> getInterviewPositionTagsList(@Param("tId") String tId);
}
