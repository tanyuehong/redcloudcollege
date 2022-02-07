package com.redskt.classroom.service;

import com.redskt.classroom.entity.OpBlogDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2021-11-17
 */
public interface OpBlogDetailService extends IService<OpBlogDetail> {
    int updateBlogGoodCount(boolean isAdd,String bid);
}
