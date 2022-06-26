package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedBlogDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedUserStateVo;
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
public interface RedBlogDetailMapper extends BaseMapper<RedBlogDetail> {

    List<RedClassBlogDetailVo> getFocusBlogList(@Param("uid") String uid,@Param("sort") int sort,@Param("size") int size);

    List<RedClassBlogDetailVo> getIndexBlogList(@Param("type") String tyid,@Param("tid") String tid,@Param("sort") int sort,@Param("size") int size);

    List<RedClassBlogDetailVo> getRedBlogDetailList(@Param("size") int size,@Param("type") int type,@Param("uid") String uid);

    List<RedClassBlogDetailVo> getGoodDetailList(@Param("size") int size,@Param("uid") String uid);

    List<RedClassBlogDetailVo> getCollectDetailList(@Param("size") int size,@Param("uid") String uid);

    RedClassBlogDetailVo getRedClassBlogDetail(@Param("bid") String bid);

    RedUserStateVo getBlogUserStatus(@Param("bid") String bid, @Param("uid") String uid);

    int addBlogGoodCount(@Param("bid") String bid);

    int prepBlogGoodCount(@Param("bid") String bid);

    int updateReadCount(@Param("bid") String bid);
}
