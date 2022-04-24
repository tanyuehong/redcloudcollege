package com.redskt.classroom.service;

import com.redskt.classroom.entity.OpBlogDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedClassBlogDetailVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2021-11-17
 */
public interface RedBlogDetailService extends IService<OpBlogDetail> {

    RedClassBlogDetailVo getRedClassBlogDetail(String bid);

    int updateBlogGoodCount(boolean isAdd,String bid);

    int updateReadCount(String bid);
}
