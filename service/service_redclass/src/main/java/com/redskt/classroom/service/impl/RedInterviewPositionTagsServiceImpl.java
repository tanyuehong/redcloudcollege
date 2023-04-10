package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedInterviewPositionTags;
import com.redskt.classroom.entity.admin.vo.RedInterviewPositionTagsVo;
import com.redskt.classroom.mapper.RedInterviewPositionTagsMapper;
import com.redskt.classroom.service.RedInterviewPositionTagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2023-04-09
 */
@Service
public class RedInterviewPositionTagsServiceImpl extends ServiceImpl<RedInterviewPositionTagsMapper, RedInterviewPositionTags> implements RedInterviewPositionTagsService {

    public List<RedInterviewPositionTagsVo> getInterviewPositionTagsList(String tId) {
        return baseMapper.getInterviewPositionTagsList(tId);
    }
}
