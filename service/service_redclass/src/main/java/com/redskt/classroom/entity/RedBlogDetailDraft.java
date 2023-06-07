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
 * @since 2023-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="RedBlogDetailDraft对象", description="")
public class RedBlogDetailDraft implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章主键")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String auid;

    private String eid;

    private String title;

    private String descrb;

    private String img;

    private String type;

    private Integer blogtype;

    private String content;

    private Integer hot;

    private Integer good;

    private Integer faver;

    private Integer viewCount;

    private Integer price;

    private Integer state;

    private String tyid;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    public boolean checkIsCanSubmit() {
        if(title!=null && title.length()>0 && content!= null && content.length()>0 &&
                descrb != null && descrb.length()>0) {
            return true;
        }
        return false;
    }


}