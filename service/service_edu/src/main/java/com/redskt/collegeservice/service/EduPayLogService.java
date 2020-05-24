package com.redskt.collegeservice.service;

import com.redskt.collegeservice.entity.EduPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-24
 */
public interface EduPayLogService extends IService<EduPayLog> {

    Map createNatvie(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);

    void updateOrdersStatus(Map<String, String> map);
}
