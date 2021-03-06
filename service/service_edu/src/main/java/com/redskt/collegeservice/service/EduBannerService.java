package com.redskt.collegeservice.service;

import com.redskt.collegeservice.entity.front.EduBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-14
 */
public interface EduBannerService extends IService<EduBanner> {

    List<EduBanner> selectAllBanner();
}
