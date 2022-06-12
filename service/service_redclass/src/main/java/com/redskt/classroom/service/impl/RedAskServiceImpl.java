package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedAskQuestion;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
import com.redskt.classroom.mapper.RedAskMapper;
import com.redskt.classroom.service.RedAskService;
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
 * @since 2020-07-26
 */
@Service
public class RedAskServiceImpl extends ServiceImpl<RedAskMapper, RedAskQuestion> implements RedAskService {

    @Override
    public int updateQustionState(String qId, String uId,int state) {
        return baseMapper.updateQustionState(qId,uId,state);
    }

    @Override
    public List<RedClassAskQuestionVo> getCollectQustionLists(int size,String uid) {
       return baseMapper.getCollectQustionLists(uid,size);
    }

    @Override
    public List<RedClassAskQuestionVo> getGoodQustionLists(int size,String uid) {
        return baseMapper.getGoodQustionLists(uid,size);
    }

    @Override
    public Boolean saveUserAsk(RedAskQuestion userAsk) {
        baseMapper.insert(userAsk);
        return true;
    }

    @Override
    public RedClassAskQuestionVo getQustionDetail(String qId) {
        return baseMapper.getQustionDetail(qId);
    }

    @Override
    public List<RedClassAskQuestionVo> getHomeAskQustionList(int sort,String typePath,String tag) {
       if(sort == 1) {
           return baseMapper.getTopQustionLists(typePath,tag);
        } else if(sort == 2) {
           return baseMapper.getHomeQustionLists(typePath,tag);
       } else if(sort == 3) {
           return baseMapper.getReplayQustionLists(typePath,tag);
        } else if(sort == 4) {
           return baseMapper.getUnReplayQustionLists(typePath,tag);
        } else if(sort == 5) {
           return baseMapper.getReplayCountQustionLists(typePath,tag);
        } else if(sort == 6) {
           return baseMapper.getPriceQustionLists(typePath,tag);
        } else {
           return baseMapper.getTopQustionLists(typePath,tag);
       }
    }

    @Override
    public int updateQustionGoodCount(boolean isAdd, String qId) {
        if(isAdd) {
            return baseMapper.addQustionGoodCount(qId);
        } else {
            return baseMapper.prepQustionGoodCount(qId);
        }
    }

    @Override
    public int updateQustionCollectCount(boolean isAdd,String qId) {
        if(isAdd) {
            return baseMapper.addQustionCollectCount(qId);
        } else {
            return baseMapper.prepQustionCollectCount(qId);
        }
    }

    @Override
    public void updateUserAskReadCount(String qId,int readCount) {
        baseMapper.changReadCount(qId,readCount);
    }
}
