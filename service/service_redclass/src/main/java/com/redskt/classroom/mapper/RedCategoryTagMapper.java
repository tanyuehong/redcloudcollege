package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedCategoryTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedCategoryTagVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2022-01-16
 */
public interface RedCategoryTagMapper extends BaseMapper<RedCategoryTag> {
    List<RedCategoryTagVo> getAllTagList();

}
