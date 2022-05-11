package com.redskt.classroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
 * @since 2022-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="RedMessage对象", description="")
public class RedMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "回复id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String uid;

    private String title;

    private String content;

    private Integer good;

    private Integer bad;

    private Integer type;

    private Integer state;

    private Integer viewCount;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
