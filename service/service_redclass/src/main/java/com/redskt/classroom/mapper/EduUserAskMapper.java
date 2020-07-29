package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.EduUserAsk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;

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
    List<RedClassAskQuestionVo> getHomeQustionLists();
}
