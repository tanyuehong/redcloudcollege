package com.redskt.collegeservice.service.impl;

import com.redskt.collegeservice.entity.EduOrder;
import com.redskt.collegeservice.entity.EduUser;
import com.redskt.collegeservice.entity.frontvo.CourseWebVo;
import com.redskt.collegeservice.entity.query.CourseInfoVo;
import com.redskt.collegeservice.mapper.EduOrderMapper;
import com.redskt.collegeservice.service.EduCourseService;
import com.redskt.collegeservice.service.EduOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redskt.collegeservice.service.EduUserService;
import com.redskt.collegeservice.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-24
 */
@Service
public class EduOrderServiceImpl extends ServiceImpl<EduOrderMapper, EduOrder> implements EduOrderService {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduUserService userService;

    @Override
    public String createOrders(String courseId, String userId) {
        CourseWebVo courseWebVo= courseService.getBaseCourseInfo(courseId);
        EduUser user = userService.getById(userId);
        //创建Order对象，向order对象里面设置需要数据
        EduOrder order = new EduOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseWebVo.getTitle());
        order.setCourseCover(courseWebVo.getCover());
        order.setTeacherName(courseWebVo.getTeacherName());
        order.setTotalFee(courseWebVo.getPrice());
        order.setMemberId(user.getId());
        order.setMobile(user.getMobile());
        order.setNickname(user.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();
    }
}
