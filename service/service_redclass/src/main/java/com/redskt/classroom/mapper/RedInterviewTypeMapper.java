package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedCategoryTag;
import com.redskt.classroom.entity.RedInterviewType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
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
public interface RedInterviewTypeMapper extends BaseMapper<RedInterviewType> {
    List<RedCategoryTag> getInterviewTypeTagList(@Param("tid") String tid);
}
