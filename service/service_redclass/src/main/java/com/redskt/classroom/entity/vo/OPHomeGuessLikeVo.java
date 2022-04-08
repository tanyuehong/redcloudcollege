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

    private String ftitle; // 第一章节

    private String fcid;

    private String auid;

    private String authorPositon;

    private Integer reply;

    private Integer viewCount;  // 浏览量

    private Integer contentCount;

    private Integer buyCount;

    private Integer good; // 点赞的数量

    private Integer hot;

    private Integer top;

    private BigDecimal price;

    private BigDecimal oldPrice;

    private Integer hotnum;
}
