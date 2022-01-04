package com.redskt.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redskt.classroom.entity.RedClassCourse;
import com.redskt.classroom.entity.vo.RedClassCourseFrontVo;
import com.redskt.classroom.mapper.RedCourseMapper;
import com.redskt.classroom.service.RedCourseService;
import com.redskt.classroom.entity.vo.RedClassCourseWebVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-05
 */
@Service
public class RedCourseServiceImpl extends ServiceImpl<RedCourseMapper, RedClassCourse> implements RedCourseService {

    @Override
    public RedClassCourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }

    @Override
    public List<RedClassCourseWebVo> getCourseList() {
        return baseMapper.getCourseList();
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<RedClassCourse> pageCourse, RedClassCourseFrontVo courseFrontVo) {
        //2 根据讲师id查询所讲课程
        QueryWrapper<RedClassCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageCourse,wrapper);

        List<RedClassCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();//下一页
        boolean hasPrevious = pageCourse.hasPrevious();//上一页

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
