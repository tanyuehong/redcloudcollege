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
 * @since 2021-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="OpBlogDetail对象", description="")
public class OpBlogDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章主键")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String title;

    private String type;

    private String blogtype;

    private String content;

    private Integer good;

    private Integer faver;

    private Integer readcount;

    private Integer price;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}
