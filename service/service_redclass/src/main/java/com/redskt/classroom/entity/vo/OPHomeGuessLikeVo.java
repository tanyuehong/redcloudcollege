package com.redskt.classroom.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OPHomeGuessLikeVo {
    private Integer type;

    private String id;

    private String title;

    private String imgUrl;

    private String tag;

    private String content;

    private String author;

    private String authorPositon;

    private Integer reply;

    private Integer viewCount;  // 浏览量

    private Integer good; // 点赞的数量

    private Integer hot;

    private Integer top;

    private Integer price;

    private BigDecimal oldPrice;

    private Integer hotnum;
}
