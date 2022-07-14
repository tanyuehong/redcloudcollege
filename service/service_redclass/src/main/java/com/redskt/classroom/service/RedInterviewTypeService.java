package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedCategoryTag;
import com.redskt.classroom.entity.RedInterviewType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-12
 */
public interface RedInterviewTypeService extends IService<RedInterviewType> {

    List<RedCategoryTag> getInterviewTypeTagList(String tid);

}
