package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedCategoryTag;
import com.redskt.classroom.entity.RedInterviewType;
import com.redskt.classroom.mapper.RedInterviewTypeMapper;
import com.redskt.classroom.service.RedInterviewTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-12
 */
@Service
public class RedInterviewTypeServiceImpl extends ServiceImpl<RedInterviewTypeMapper, RedInterviewType> implements RedInterviewTypeService {

    @Override
    public List<RedCategoryTag> getInterviewTypeTagList(String tid) {
        return baseMapper.getInterviewTypeTagList(tid);
    }

}
