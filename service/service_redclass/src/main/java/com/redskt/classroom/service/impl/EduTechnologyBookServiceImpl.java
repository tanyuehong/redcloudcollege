package com.redskt.classroom.service.impl;

import com.redskt.classroom.entity.RedClassBook;
import com.redskt.classroom.entity.vo.RedClassBookVo;
import com.redskt.classroom.mapper.EduTechnologyBookMapper;
import com.redskt.classroom.service.EduTechnologyBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2021-02-28
 */
@Service
public class EduTechnologyBookServiceImpl extends ServiceImpl<EduTechnologyBookMapper, RedClassBook> implements EduTechnologyBookService {
    @Override
    public List<RedClassBookVo> getBookIndexInfo() {
        return baseMapper.getBookIndexInfo();
    }
}
