package com.redskt.classroom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.RedClassBanner;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-14
 */
public interface RedBannerService extends IService<RedClassBanner> {
    List<RedClassBanner> selectAllBanner();
}
