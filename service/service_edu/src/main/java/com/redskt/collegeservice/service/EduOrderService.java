package com.redskt.collegeservice.service;

import com.redskt.collegeservice.entity.EduOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-24
 */
public interface EduOrderService extends IService<EduOrder> {

    String createOrders(String courseId, String userId);
}
