package com.redskt.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redskt.classroom.entity.RedClassVideo;
import com.redskt.classroom.mapper.RedVideoMapper;
import com.redskt.classroom.service.RedVideoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-05
 */
@Service
public class RedVideoServiceImpl extends ServiceImpl<RedVideoMapper, RedClassVideo> implements RedVideoService {
}
