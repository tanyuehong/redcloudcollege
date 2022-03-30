package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.OpBlogDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2021-11-17
 */
public interface OpBlogDetailMapper extends BaseMapper<OpBlogDetail> {
    int addBlogGoodCount(@Param("bid")  String bid);

    int prepBlogGoodCount(@Param("bid")  String bid);

    int updateReadCount(@Param("bid")  String bid);
}
