package com.redskt.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.RedBlogDetail;
import com.redskt.classroom.entity.RedBlogDetailDraft;
import com.redskt.classroom.mapper.RedBlogDetailDraftMapper;
import com.redskt.classroom.service.RedBlogDetailDraftService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2023-06-05
 */
@Service
public class RedBlogDetailDraftServiceImpl extends ServiceImpl<RedBlogDetailDraftMapper, RedBlogDetailDraft> implements RedBlogDetailDraftService {

    @Override
    public boolean removeDraft(String bId,String uId) {
        QueryWrapper<RedBlogDetailDraft> draftQueryWrapper = new QueryWrapper<>();
        draftQueryWrapper.eq("id", bId);
        draftQueryWrapper.eq("auid", uId);
        return remove(draftQueryWrapper);
    }
}
