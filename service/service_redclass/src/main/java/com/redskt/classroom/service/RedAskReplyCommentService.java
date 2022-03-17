package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedAskReplyComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.ReplyCommentVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-01-21
 */
public interface RedAskReplyCommentService extends IService<RedAskReplyComment> {

    ReplyCommentVo getUerCommentOne(String uId);

    int updateReplyCommentGoodCount(boolean isAdd,String cId);
}
