package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedBlogDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedUserStateVo;
import com.redskt.classroom.entity.vo.RedClassBlogDetailVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2021-11-17
 */
public interface RedBlogDetailService extends IService<RedBlogDetail> {

    RedClassBlogDetailVo getRedClassBlogDetail(String bid);

    List<RedClassBlogDetailVo> getGoodDetailList(int size,String uid);

    List<RedClassBlogDetailVo> getCollectDetailList(int size,String uid);

    List<RedClassBlogDetailVo> getRedBlogDetailList(int size,int type,String uid);

    RedUserStateVo getBlogUserStatus(String bid, String uid);

    int updateBlogGoodCount(boolean isAdd,String bid);

    int updateReadCount(String bid);
}
