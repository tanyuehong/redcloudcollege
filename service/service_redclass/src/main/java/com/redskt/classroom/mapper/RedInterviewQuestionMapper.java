package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedInterviewQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedInterviewQuestionVo;
import com.redskt.classroom.entity.vo.RedUserStateVo;
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

    int updateQuestionReadCount(@Param("id")  String qId,@Param("readCount") int readCount);

    RedInterviewQuestionVo getQustionDetail(String qId);

    int addGoodCount(@Param("qid")  String qId);

    int prepGoodCount(@Param("qid")  String qId);

    int addCollectCount(@Param("qid")  String qId);

    int prepCollectCount(@Param("qid")  String qId);

    RedUserStateVo getUserStatus(@Param("qid") String qid, @Param("uid") String uid);

}
