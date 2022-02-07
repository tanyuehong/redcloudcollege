package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedBlogGood;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-02-07
 */
public interface RedBlogGoodService extends IService<RedBlogGood> {
    int updateGoodState(String uid,String bid);
}
