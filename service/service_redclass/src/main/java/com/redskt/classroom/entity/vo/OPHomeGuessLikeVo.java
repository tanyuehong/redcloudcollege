package com.redskt.classroom.entity.vo;

import lombok.Data;

@Data
public class OPHomeGuessLikeVo {
    private Integer type;

    private String id;

    private String title;

    private String imgUrl;

    private String tag;

    private String content;

    private Integer reply;

    private Integer viewCount;  // 浏览量

    private Integer good; // 点赞的数量

    private Integer hot;

    private Integer top;

    private Integer price;

    private Integer hotnum;
}
