package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedInterviewQuestionEveryday;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.admin.vo.RedInterViewEveryDayQuestionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2023-03-20
 */
public interface RedInterviewQuestionEverydayMapper extends BaseMapper<RedInterviewQuestionEveryday> {

    List<RedInterViewEveryDayQuestionVo> getInterViewEveryQuestionList(@Param("date") String date);
}
