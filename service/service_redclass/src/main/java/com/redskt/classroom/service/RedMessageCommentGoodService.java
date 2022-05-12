package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedMessageCommentGood;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-05-12
 */
public interface RedMessageCommentGoodService extends IService<RedMessageCommentGood> {

    int updateCommentGoodState(String uid, String cId, int type);
}
