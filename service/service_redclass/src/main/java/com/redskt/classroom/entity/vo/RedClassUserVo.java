package com.redskt.classroom.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class RedClassUserVo {

    private String id;

    private String openid;

    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    private String name;

    @ApiModelProperty(value = "性别 1 女，2 男")
    private Integer sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "关注数量")
    private int focus;   // 粉丝数量

    private int mfocus;  // 自己关注了多少人

    private int viewsum; // 文章总阅读数

    private int bgoodsum; // 文章总点赞数

    private int practice; // 文章总点赞数

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "公司")
    private String company;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "老师职级")
    private String tposition;

    @ApiModelProperty(value = "老师类型")
    private String ttype;

    @ApiModelProperty(value = "老师关键字")
    private String keywords;

    @ApiModelProperty(value = "讲师简介")
    private String intro;

    @ApiModelProperty(value = "个人主页")
    private String perpage;

    @ApiModelProperty(value = "个人介绍")
    private String perintroduction;

    @ApiModelProperty(value = "用户签名")
    private String sign;

    @ApiModelProperty(value = "讲师资历,一句话说明讲师")
    private String career;

    @ApiModelProperty(value = "是否禁用 1（true）已禁用，  0（false）未禁用")
    private Boolean isDisabled;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;
}
