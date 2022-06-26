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
@ApiModel(value="OpBlogType对象", description="")
@TableName("op_blog_type")
public class RedBlogType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类型ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String name;

    private String type;

    @TableField("parentId")
    private String parentId;

    private Integer bsort;

    private Integer level;

    private Integer hot;

    private String describ;

    private String keywords;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
