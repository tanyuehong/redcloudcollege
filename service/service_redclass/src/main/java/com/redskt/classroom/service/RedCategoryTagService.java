package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedCategoryTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedCategoryTagVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-01-16
 */
public interface RedCategoryTagService extends IService<RedCategoryTag> {

    List<RedCategoryTagVo> getAllTagList();

}
