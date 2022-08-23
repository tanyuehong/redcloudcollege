package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedInterviewComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedCommentVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-25
 */
public interface RedInterviewCommentService extends IService<RedInterviewComment> {

    List<RedCommentVo> getRedCommentList(String id, String uId,Integer rSize, int type);

    RedCommentVo getCommentOne(String cId);

    int updateGoodCount(boolean isAdd,String cId);

}
