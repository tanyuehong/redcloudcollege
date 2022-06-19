package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedCategoryTag;
import com.redskt.classroom.entity.vo.RedCategoryTagVo;
import com.redskt.classroom.mapper.RedCategoryTagMapper;
import com.redskt.classroom.service.RedCategoryTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-01-16
 */
@Service
public class RedCategoryTagServiceImpl extends ServiceImpl<RedCategoryTagMapper, RedCategoryTag> implements RedCategoryTagService {
    @Override
    public List<RedCategoryTagVo> getAllTagList() {
        return baseMapper.getAllTagList();
    }
}
