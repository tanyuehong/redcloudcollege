package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.OpBlogDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedBlogUserVo;
import com.redskt.classroom.entity.vo.RedClassBlogDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2021-11-17
 */
public interface OpBlogDetailMapper extends BaseMapper<OpBlogDetail> {

    List<RedClassBlogDetailVo> getRedBlogDetailList(@Param("size") int size,@Param("type") int type);

    RedClassBlogDetailVo getRedClassBlogDetail(@Param("bid") String bid);

    RedBlogUserVo getBlogUserStatus(@Param("bid") String bid,@Param("uid") String uid);

    int addBlogGoodCount(@Param("bid") String bid);

    int prepBlogGoodCount(@Param("bid") String bid);

    int updateReadCount(@Param("bid") String bid);
}
