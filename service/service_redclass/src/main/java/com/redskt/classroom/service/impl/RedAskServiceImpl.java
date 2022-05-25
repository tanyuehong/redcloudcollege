package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.EduUserAsk;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
import com.redskt.classroom.mapper.RedAskMapper;
import com.redskt.classroom.service.RedAskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class RedAskServiceImpl extends ServiceImpl<RedAskMapper, EduUserAsk> implements RedAskService {

    @Override
    public List<RedClassAskQuestionVo> getCollectQustionLists(int size,String uid) {
       return baseMapper.getCollectQustionLists(uid,size);
    }

    @Override
    public Boolean saveUserAsk(EduUserAsk userAsk) {
        baseMapper.insert(userAsk);
        return true;
    }

    @Override
    public RedClassAskQuestionVo getQustionDetail(String qId) {
        return baseMapper.getQustionDetail(qId);
    }

    @Override
    public List<RedClassAskQuestionVo> getHomeAskQustionList(int type,String typeId) {
       if(type == 2) {
            return baseMapper.getReplayQustionLists(typeId);
        } else if(type == 3) {
            return baseMapper.getUnReplayQustionLists(typeId);
        } else if(type == 4) {
            return baseMapper.getReplayCountQustionLists(typeId);
        } else if(type == 5) {
            return baseMapper.getTopQustionLists(typeId);
        } else if(type == 6) {
            return baseMapper.getPriceQustionLists(typeId);
        } else {
           return baseMapper.getHomeQustionLists(typeId);
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
