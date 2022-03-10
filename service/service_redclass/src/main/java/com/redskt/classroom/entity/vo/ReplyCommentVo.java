package com.redskt.classroom.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ReplyCommentVo {

    private String id;

    private String rid;

    private String content;

    private String uid;

    private String name;

    private String touid;

    private String toname;

    private String avatar;

    private Date gmtCreate;

}
