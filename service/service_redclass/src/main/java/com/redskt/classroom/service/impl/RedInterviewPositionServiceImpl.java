package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedCategoryTag;
import com.redskt.classroom.entity.RedInterviewPosition;
import com.redskt.classroom.mapper.RedInterviewPositionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redskt.classroom.service.RedInterviewPositionService;
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
public class RedInterviewPositionServiceImpl extends ServiceImpl<RedInterviewPositionMapper, RedInterviewPosition> implements RedInterviewPositionService {

    @Override
    public List<RedCategoryTag> getInterviewTypeTagList(String tid) {
        return baseMapper.getInterviewTypeTagList(tid);
    }

}
