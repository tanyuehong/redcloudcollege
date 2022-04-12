package com.redskt.classroom.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RedClassBlogDetailVo {
    private String id;

    private String title;

    private String aName;

    private String aUid;

    private String aAvatar;

    private String type;

    private String blogtype;

    private String content;

    private String descrb;

    private Integer hot;

    private Integer good;

    private Integer faver;

    private Integer viewCount;

    private Integer price;

    private Date gmtCreate;

    private Date gmtModified;
}
