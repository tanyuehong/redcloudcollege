package com.redskt.classroom.service;

import com.redskt.classroom.entity.OpBlogDetail;
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
public interface RedBlogDetailService extends IService<OpBlogDetail> {

    RedClassBlogDetailVo getRedClassBlogDetail(String bid);

    List<RedClassBlogDetailVo> getRedBlogDetailList(int size,int type);

    RedUserStateVo getBlogUserStatus(String bid, String uid);

    int updateBlogGoodCount(boolean isAdd,String bid);

    int updateReadCount(String bid);
}
