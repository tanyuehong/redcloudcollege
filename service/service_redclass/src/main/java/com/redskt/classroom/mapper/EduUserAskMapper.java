package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.EduUserAsk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2020-07-26
 */
public interface EduUserAskMapper extends BaseMapper<EduUserAsk> {
    List<RedClassAskQuestionVo> getHomeQustionLists(@Param("qustype")  String qustype);

    List<RedClassAskQuestionVo> getReplayQustionLists(@Param("qustype")  String qustype);

    List<RedClassAskQuestionVo> getUnReplayQustionLists(@Param("qustype")  String qustype);

    List<RedClassAskQuestionVo> getReplayCountQustionLists(@Param("qustype")  String qustype);

    List<RedClassAskQuestionVo> getTopQustionLists(@Param("qustype")  String qustype);

    List<RedClassAskQuestionVo> getPriceQustionLists(@Param("qustype")  String qustype);

    RedClassAskQuestionVo getQustionDetail(String tId);

    int changReadCount(@Param("id")  String qId,@Param("readCount") int readCount);

    int addQustionGoodCount(@Param("qid")  String qId);

    int prepQustionGoodCount(@Param("qid")  String qId);

    int addQustionCollectCount(@Param("qid")  String qId);

    int prepQustionCollectCount(@Param("qid")  String qId);
}
