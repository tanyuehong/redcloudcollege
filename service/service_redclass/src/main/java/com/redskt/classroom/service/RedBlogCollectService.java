package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedBlogCollect;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-04-30
 */
public interface RedBlogCollectService extends IService<RedBlogCollect> {
    int updateBlogCollectState(String bid,String uid);
}
