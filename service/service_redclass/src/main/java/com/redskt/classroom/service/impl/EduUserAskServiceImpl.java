package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.EduUserAsk;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
import com.redskt.classroom.mapper.EduUserAskMapper;
import com.redskt.classroom.service.EduUserAskService;
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
public class EduUserAskServiceImpl extends ServiceImpl<EduUserAskMapper, EduUserAsk> implements EduUserAskService {

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
}
