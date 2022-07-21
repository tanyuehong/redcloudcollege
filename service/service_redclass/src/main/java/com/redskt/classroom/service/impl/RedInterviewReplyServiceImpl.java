package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedInterviewReply;
import com.redskt.classroom.mapper.RedInterviewReplyMapper;
import com.redskt.classroom.service.RedInterviewReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-21
 */
@Service
public class RedInterviewReplyServiceImpl extends ServiceImpl<RedInterviewReplyMapper, RedInterviewReply> implements RedInterviewReplyService {

    @Override
    public List<RedInterviewReply> getInterViewReply(String qId) {
        return new ArrayList<RedInterviewReply>();
    }
}
