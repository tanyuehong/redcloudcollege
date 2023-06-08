package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedBlogDetailDraft;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2023-06-05
 */
public interface RedBlogDetailDraftService extends IService<RedBlogDetailDraft> {

     boolean removeDraft(String bId,String uId);

}
