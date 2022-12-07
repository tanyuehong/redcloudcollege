package com.redskt.classroom.entity.vo;

import com.redskt.commonutils.R;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class RedClassRegisterVo {
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "页面key")
    private String pageKey;
    @ApiModelProperty(value = "图片验证码")
    private String verCode;

    public boolean checkParameter() {
       return StringUtils.isEmpty(this.mobile) || StringUtils.isEmpty(this.verCode);
    }
}