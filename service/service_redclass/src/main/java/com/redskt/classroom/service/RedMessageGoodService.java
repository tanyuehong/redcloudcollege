package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedMessageGood;
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
public interface RedMessageGoodService extends IService<RedMessageGood> {

    int updateGoodState(String uid,String mid);
}
