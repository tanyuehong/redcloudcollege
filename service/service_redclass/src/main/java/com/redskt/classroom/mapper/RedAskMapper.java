package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedAskQuestion;
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
 * @since 2020-07-26
 */
public interface RedAskMapper extends BaseMapper<RedAskQuestion> {

    List<RedClassAskQuestionVo> getHomeQustionLists(@Param("qpath") String qpath,@Param("tag") String tag);
    List<RedClassAskQuestionVo> getReplayQustionLists(@Param("qpath") String qpath,@Param("tag") String tag);
    List<RedClassAskQuestionVo> getUnReplayQustionLists(@Param("qpath") String qpath,@Param("tag") String tag);
    List<RedClassAskQuestionVo> getReplayCountQustionLists(@Param("qpath") String qpath,@Param("tag") String tag);
    List<RedClassAskQuestionVo> getTopQustionLists(@Param("qpath") String qpath,@Param("tag") String tag);
    List<RedClassAskQuestionVo> getPriceQustionLists(@Param("qpath") String qpath,@Param("tag") String tag);

    List<RedClassAskQuestionVo> getGoodQustionLists(@Param("uid")  String uid,@Param("size")  int size);

    List<RedClassAskQuestionVo> getCollectQustionLists(@Param("uid")  String uid,@Param("size")  int size);

    RedClassAskQuestionVo getQustionDetail(String tId);

    int updateQustionState(@Param("qid")  String qId,@Param("uid")  String uId,@Param("state") int state);

    int changReadCount(@Param("id")  String qId,@Param("readCount") int readCount);

    int addQustionGoodCount(@Param("qid")  String qId);

    int prepQustionGoodCount(@Param("qid")  String qId);

    int addQustionCollectCount(@Param("qid")  String qId);

    int prepQustionCollectCount(@Param("qid")  String qId);
}
