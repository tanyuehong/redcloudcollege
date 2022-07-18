package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedInterviewQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
import com.redskt.classroom.entity.vo.RedInterviewQuestionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-14
 */
public interface RedInterviewQuestionMapper extends BaseMapper<RedInterviewQuestion> {

    List<RedInterviewQuestionVo> getHomeInterviewQustionList(@Param("sort") int sort, @Param("tag") String tag);

}
