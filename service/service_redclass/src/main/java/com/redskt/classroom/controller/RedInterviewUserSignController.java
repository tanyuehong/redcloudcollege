package com.redskt.classroom.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.RedInterviewUserSign;
import com.redskt.classroom.service.RedInterviewUserSignService;
import com.redskt.commonutils.R;
import com.redskt.commonutils.RequestParmUtil;
import com.redskt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2023-02-22
 */
@RestController
@RequestMapping("/interview")
public class RedInterviewUserSignController {

    @Autowired
    private RedInterviewUserSignService signService;

    @PostMapping("commitSign")
    public R commitSign(@RequestBody Map parameterMap, HttpServletRequest request) {
        parameterMap = RequestParmUtil.transToMAP(parameterMap);
        String signDate = (String) parameterMap.get("date");
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (uId.length()>0 && signDate.length()>0) {
            RedInterviewUserSign sign = new RedInterviewUserSign();
            sign.setUid(uId);
            sign.setDate(signDate);
            if (signService.save(sign)) {
                return R.okSucessTips("签到成功");
            }
            return R.error("签到异常，请重新尝试哈～");
        }
        return R.errorParam();
    }

    @GetMapping("getSignDateList")
    public R getSignDateList(HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);


        QueryWrapper<RedInterviewUserSign> signQueryWrapper = new QueryWrapper<>();
        signQueryWrapper.eq("uid",uId);
        signQueryWrapper.orderByAsc("gmtCreate");
        
        List<RedInterviewUserSign> signList = signService.list(signQueryWrapper);
        List<String> dateList = new ArrayList<>();

        for (int i=0;i<signList.size();i++) {
            dateList.add(signList.get(i).getDate());
        }
        return R.ok().data("dateList",dateList);
    }



}

