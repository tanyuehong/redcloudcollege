package com.redskt.classroom.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author tanyuehong
 * @since 2022-07-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="RedInterviewQuestion对象", description="")
public class RedInterviewQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问题id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String uid;

    private String title;

    private String type;

    private String deep;

    private String content;

    private String email;

    private Integer coment;

    private Integer hotnum;

    private Integer readcount;

    private Integer collect;

    private Integer price;

    private Integer good;

    private String qustype;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
