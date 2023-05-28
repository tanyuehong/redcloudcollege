package com.redskt.classroom.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

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
@TableName("op_blog_detail")
public class RedBlogDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章主键")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String title;

    private String auid;

    private String type;

    private String blogtype;

    private String content;

    private String descrb;

    private Integer hot;

    private Integer good;

    private Integer faver;

    private Integer viewCount;

    private Integer price;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
