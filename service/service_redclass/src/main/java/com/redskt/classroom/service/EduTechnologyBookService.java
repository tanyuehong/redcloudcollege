package com.redskt.classroom.service;

import com.redskt.classroom.entity.RedClassBook;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.vo.RedClassBookVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2021-02-28
 */
public interface EduTechnologyBookService extends IService<RedClassBook> {
    List<RedClassBookVo> getBookIndexInfo();
}
