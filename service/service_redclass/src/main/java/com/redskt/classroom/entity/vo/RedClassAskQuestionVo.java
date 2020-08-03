package com.redskt.classroom.entity.vo;

import lombok.Data;

@Data
public class RedClassAskQuestionVo {

    private String qId;

    private String nickname;

    private String title;

    private Integer type;

    private String tag;

    private String content;

    private Integer reply;

    private Integer read;

    private Integer hot;

    private Integer top;

    private Integer price;

    private Integer hotnum;
}
