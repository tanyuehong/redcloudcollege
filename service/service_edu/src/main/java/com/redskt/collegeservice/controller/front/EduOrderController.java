package com.redskt.collegeservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.collegeservice.entity.front.EduOrder;
import com.redskt.collegeservice.service.EduOrderService;
import com.redskt.commonutils.JwtUtils;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-24
 */
@RestController
@RequestMapping("/collegeservice/order")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class EduOrderController {

    @Autowired
    private EduOrderService orderService;

    //1 生成订单的方法
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        //创建订单，返回订单号
        String orderNo =
                orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderNo);
    }

    //2 根据订单id查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<EduOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        EduOrder order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }

}

