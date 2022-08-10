package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedInterviewQuestion;
import com.redskt.classroom.entity.vo.RedInterviewQuestionVo;
import com.redskt.classroom.entity.vo.RedUserStateVo;
import com.redskt.classroom.mapper.RedInterviewQuestionMapper;
import com.redskt.classroom.service.RedInterviewQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-14
 */
@Service
public class RedInterviewQuestionServiceImpl extends ServiceImpl<RedInterviewQuestionMapper, RedInterviewQuestion> implements RedInterviewQuestionService {

    @Override
    public List<RedInterviewQuestionVo> getHomeInterviewQustionList(String sort, String tag) {
        int sortInt  = 1;
        if(sort.equals("latest")) {
            sortInt = 2;
        }
        if(sort.equals("hot")) {
            sortInt = 3;
        }
        if(tag.length()==0) {
            tag = null;
        }
        return baseMapper.getHomeInterviewQustionList(sortInt, tag);
    }

    @Override
    public void updateQuestionReadCount(String qId,int readCount) {
        baseMapper.updateQuestionReadCount(qId,readCount);
    }

    @Override
    public RedInterviewQuestionVo getQustionDetail(String qId) {
        return baseMapper.getQustionDetail(qId);
    }

    @Override
    public int updateGoodCount(boolean isAdd,String qId) {
        if(isAdd) {
            return baseMapper.addGoodCount(qId);
        } else {
            return baseMapper.prepGoodCount(qId);
        }
    }

    @Override
    public int updateCollectCount(boolean isAdd,String qId) {
        if(isAdd) {
            return baseMapper.addCollectCount(qId);
        } else {
            return baseMapper.prepCollectCount(qId);
        }
    }

    @Override
    public RedUserStateVo getUserStatus(String qid, String uid) {
        return baseMapper.getUserStatus(qid, uid);
    }
}
