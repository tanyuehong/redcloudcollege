package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedInterviewQuestionPosition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.admin.vo.RedInterviewQuestionPositionVo;
import com.redskt.classroom.entity.vo.RedInterviewQuestionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-10-20
 */
public interface RedInterviewQuestionPositionMapper extends BaseMapper<RedInterviewQuestionPosition> {

    List<RedInterviewQuestionPositionVo> getQuestionPositionClassifyList(@Param("qId") String qId);

}
