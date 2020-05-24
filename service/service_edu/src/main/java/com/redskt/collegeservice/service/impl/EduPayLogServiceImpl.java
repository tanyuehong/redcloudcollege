package com.redskt.collegeservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.collegeservice.entity.EduOrder;
import com.redskt.collegeservice.entity.EduPayLog;
import com.redskt.collegeservice.mapper.EduPayLogMapper;
import com.redskt.collegeservice.service.EduOrderService;
import com.redskt.collegeservice.service.EduPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redskt.servicebase.excepionhandler.RedCloudCollegeExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-24
 */
@Service
public class EduPayLogServiceImpl extends ServiceImpl<EduPayLogMapper, EduPayLog> implements EduPayLogService {

    @Autowired
    private EduOrderService orderService;

    @Override
    public Map createNatvie(String orderNo) {
        try {
            //1 根据订单号查询订单信息
            QueryWrapper<EduOrder> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            EduOrder order = orderService.getOne(wrapper);

            //2 使用map设置生成二维码需要参数
//            Map m = new HashMap();
//            m.put("appid","wx74862e0dfcf69954");
//            m.put("mch_id", "1558950191");
//            m.put("nonce_str", WXPayUtil.generateNonceStr());
//            m.put("body", order.getCourseTitle()); //课程标题
//            m.put("out_trade_no", orderNo); //订单号
//            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
//            m.put("spbill_create_ip", "127.0.0.1");
//            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
//            m.put("trade_type", "NATIVE");
//
//            //3 发送httpclient请求，传递参数xml格式，微信支付提供的固定的地址
//            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
//            //设置xml格式的参数
//            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
//            client.setHttps(true);
//            //执行post请求发送
//            client.post();

            //4 得到发送请求返回结果
            //返回内容，是使用xml格式返回
           // String xml = client.getContent();

            //把xml格式转换map集合，把map集合返回
            //Map<String,String> resultMap = WXPayUtil.xmlToMap(xml);

            //最终返回数据 的封装
            Map map = new HashMap();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", 200);  //返回二维码操作状态码
            map.put("code_url", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590342889033&di=6c172ecd31e8140052717c47d3fafb87&imgtype=0&src=http%3A%2F%2Fimg.yoyou.com%2Fuploadfile%2F2017%2F0727%2F20170727014939103.jpg");        //二维码地址

            return map;
        }catch(Exception e) {
            throw new RedCloudCollegeExceptionHandler(20001,"生成二维码失败");
        }
    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        return null;
    }

    @Override
    public void updateOrdersStatus(Map<String, String> map) {
        //从map获取订单号
        String orderNo = map.get("out_trade_no");
        //根据订单号查询订单信息
        QueryWrapper<EduOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        EduOrder order = orderService.getOne(wrapper);

        //更新订单表订单状态
        if(order.getStatus().intValue() == 1) { return; }
        order.setStatus(1);//1代表已经支付
        orderService.updateById(order);

        //向支付表添加支付记录
        EduPayLog payLog = new EduPayLog();
        payLog.setOrderNo(orderNo);  //订单号
        payLog.setPayTime(new Date()); //订单完成时间
        payLog.setPayType(1);//支付类型 1微信
        payLog.setTotalFee(order.getTotalFee());//总金额(分)

        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id")); //流水号
        payLog.setAttr(JSONObject.toJSONString(map));

        baseMapper.insert(payLog);
    }
}
