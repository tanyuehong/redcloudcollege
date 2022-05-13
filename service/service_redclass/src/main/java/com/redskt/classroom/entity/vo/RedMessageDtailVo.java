package com.redskt.classroom.entity.vo;

import lombok.Data;
import java.util.Date;

@Data
public class RedMessageDtailVo {
    private String id;

    private String title;

    private String authorName;

    private String authorUid;

    private String authorAvatar;

    private String type;

    private String content;

    private Integer good;

    private Integer viewCount;

    private Integer allvcount;

    private Integer fanscount;

    private int cCount;

    private Date gmtCreate;

    private Date gmtModified;
}
