package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedQustionCollect;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-02-09
 */
public interface RedQustionCollectService extends IService<RedQustionCollect> {
    int updateQustionCollectState(String uid,String qId);
}
