package com.redskt.classroom.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RedClassAskQuestionVo {

    private String qId;

    private String uid;

    private String nickname;

    private String avatar;

    private String title;

    private Integer type;

    private String tag;

    private String typeString;

    private String subString;

    private String content;

    private Integer reply;

    private Integer readcount;

    private Integer collect;

    private Integer good;

    private Integer price;

    private Integer hotnum;

    private int state;

    private Date gmtCreate;
}
