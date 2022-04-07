package com.redskt.classroom.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RedClassBookVo {

    private String id;

    private String title;

    private String imgUrl;

    private String describ;

    private String author;

    private String authorPositon;

    private String bookDetail;

    private Integer buyCount;

    private Integer viewCount;

    private BigDecimal price;

    private BigDecimal oldPrice;

    private Date gmtCreate;

    private Date gmtModified;

}
