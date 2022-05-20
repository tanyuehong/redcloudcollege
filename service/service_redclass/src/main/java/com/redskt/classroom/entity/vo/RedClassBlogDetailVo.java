package com.redskt.classroom.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RedClassBlogDetailVo {
    private String id;

    private String title;

    private String authorName;

    private String authorUid;

    private String authorAvatar;

    private String type;   // 文章属于什么类型  前端，后端，等

    private String blogtype;

    private String content;

    private String descrb;

    private String cover;

    private Integer hot;

    private Integer good;

    private Integer ctype;  // 列表显示类型  blog  ,通知

    private Integer faver;

    private Integer viewCount;

    private int cCount;

    private Integer price;

    private Date gmtCreate;

    private Date gmtModified;
}
