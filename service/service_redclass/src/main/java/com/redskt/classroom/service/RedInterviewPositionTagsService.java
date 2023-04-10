package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedInterviewPositionTags;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.admin.vo.RedInterviewPositionTagsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2023-04-09
 */
public interface RedInterviewPositionTagsService extends IService<RedInterviewPositionTags> {

    List<RedInterviewPositionTagsVo> getInterviewPositionTagsList(String tId);
}
