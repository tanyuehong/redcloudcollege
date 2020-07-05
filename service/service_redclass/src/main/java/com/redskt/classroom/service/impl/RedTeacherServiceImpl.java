package com.redskt.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.redskt.classroom.entity.RedClassTeacher;
import com.redskt.classroom.mapper.RedTeacherMapper;
import com.redskt.classroom.service.RedTeacherService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-04-26
 */
@Service
public class RedTeacherServiceImpl extends ServiceImpl<RedTeacherMapper, RedClassTeacher> implements RedTeacherService {

    @Override
    public Map<String, Object> getTeacherFrontList(Page<RedClassTeacher> pageTeacher) {
        QueryWrapper<RedClassTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //把分页数据封装到pageTeacher对象
        baseMapper.selectPage(pageTeacher,wrapper);

        List<RedClassTeacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();
        boolean hasNext = pageTeacher.hasNext();//下一页
        boolean hasPrevious = pageTeacher.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }
}
