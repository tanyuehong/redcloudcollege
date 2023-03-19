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

    List<RedInterviewQuestionVo> getHomeInterviewQuestionList(@Param("sort") int sort,@Param("size") int size, @Param("tag") String tag,@Param("qustype") String qType,@Param("qid") String qId);

    List<RedInterviewQuestionVo> getPositionQuestionList(@Param("sort") int sort,@Param("size") int size,@Param("pid") String pId,@Param("orderType") int orderType);

    int updateQuestionReadCount(@Param("id")  String qId,@Param("readCount") int readCount);

    RedInterviewQuestionVo getQuestionDetail(String qId);

    int addGoodCount(@Param("qid")  String qId);

    int prepGoodCount(@Param("qid")  String qId);

    int addCollectCount(@Param("qid")  String qId);

    int prepCollectCount(@Param("qid")  String qId);

    RedUserStateVo getUserStatus(@Param("qid") String qid, @Param("uid") String uid);

    int updateQustionState(@Param("qid")  String qId,@Param("uid")  String uId,@Param("state") int state);

    int addSmeetCount(@Param("qid")  String qId);
    int addTmeetCount(@Param("qid")  String qId);
    int addPmeetCount(@Param("qid")  String qId);
    int addUmeetCount(@Param("qid")  String qId);

}
