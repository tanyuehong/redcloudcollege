package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedCategoryTag;
import com.redskt.classroom.entity.RedInterviewPosition;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-12
 */
public interface RedInterviewPositionService extends IService<RedInterviewPosition> {

    List<RedCategoryTag> getInterviewTypeTagList(String tid);

}
