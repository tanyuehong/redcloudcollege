package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedInterviewQuestion;
import com.redskt.classroom.entity.vo.RedInterviewQuestionVo;
import com.redskt.classroom.entity.vo.RedUserStateVo;
import com.redskt.classroom.mapper.RedInterviewQuestionMapper;
import com.redskt.classroom.service.RedInterviewQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
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
        return baseMapper.getHomeInterviewQuestionList(sortInt,20, tag,null,null);
    }

    @Override
    public List<RedInterviewQuestionVo> getHotInterviewQustionList(String qType,String qId) {
        return baseMapper.getHomeInterviewQuestionList(4,10, null, qType,qId);
    }

    @Override
    public List<RedInterviewQuestionVo> getPositionQuestionList(int sort,String pId,String sId,int orderType) {
        return baseMapper.getPositionQuestionList(sort,20, pId,orderType);
    }

    @Override
    public void updateQuestionReadCount(String qId,int readCount) {
        baseMapper.updateQuestionReadCount(qId,readCount);
    }

    @Override
    public RedInterviewQuestionVo getQuestionDetail(String qId) {
        return baseMapper.getQuestionDetail(qId);
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


    @Override
    public int updateQustionState(String qId,String uId,int state) {
        return baseMapper.updateQustionState(qId,uId,state);
    }

    @Override
    public void updateMeetType(String qId,int type) {
        if (type == 1) {
            baseMapper.addSmeetCount(qId);
        } else if (type == 2) {
            baseMapper.addTmeetCount(qId);
        } else if (type == 3) {
            baseMapper.addPmeetCount(qId);
        } else {
            baseMapper.addUmeetCount(qId);
        }
    }
}
