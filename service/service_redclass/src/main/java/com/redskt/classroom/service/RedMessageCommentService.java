package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedMessageComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedMessageCommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-05-12
 */
public interface RedMessageCommentService extends IService<RedMessageComment> {

    List<RedMessageCommentVo> getMessageCommentList(String mid,Integer size,Integer rsize,int type);

    RedMessageCommentVo getMessageCommentOne(String mid);

    void addCommentGoodCount(String cid);

    void prepCommentGoodCount(String cid);
}
