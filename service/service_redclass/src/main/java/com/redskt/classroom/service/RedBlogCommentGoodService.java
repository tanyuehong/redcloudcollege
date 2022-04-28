package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedBlogCommentGood;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-04-28
 */
public interface RedBlogCommentGoodService extends IService<RedBlogCommentGood> {

    int updateCommentGoodState(String uid, String cId, int type);

}
