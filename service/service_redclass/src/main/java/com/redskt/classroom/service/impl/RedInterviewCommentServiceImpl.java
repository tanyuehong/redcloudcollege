package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedInterviewComment;
import com.redskt.classroom.entity.vo.RedCommentVo;
import com.redskt.classroom.mapper.RedInterviewCommentMapper;
import com.redskt.classroom.service.RedInterviewCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-25
 */
@Service
public class RedInterviewCommentServiceImpl extends ServiceImpl<RedInterviewCommentMapper, RedInterviewComment> implements RedInterviewCommentService {

    @Override
    public List<RedCommentVo> getRedCommentList(String id, String uId, Integer rSize, int type) {
        return baseMapper.getRedCommentList(id,uId,5,5, type);
    }

    @Override
    public  RedCommentVo getCommentOne(String cId) {
        return baseMapper.getCommentOne(cId);
    }

    @Override
    public int updateGoodCount(boolean isAdd,String cId) {
        if(isAdd) {
            return  baseMapper.addGoodCount(cId);
        } else {
            return baseMapper.prepGoodCount(cId);
        }
    }
}
