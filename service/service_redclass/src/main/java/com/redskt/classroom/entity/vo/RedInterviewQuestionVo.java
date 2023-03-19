package com.redskt.classroom.entity.vo;

import com.redskt.classroom.entity.RedCategoryTag;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RedInterviewQuestionVo {

    private String qId;

    private String uid;

    private String nickname;

    private String avatar;

    private String snum;

    private String title;

    private String type;

    private String qustype;

    private String deep;

    private String typeString;

    private String subString;

    private String content;

    private String answer;

    private Integer reply;

    private Integer readcount;

    private Integer collect;

    private Integer good;

    private Integer price;

    private Integer hotnum;

    private float frequency;   // 面试频率

    private int meet;

    private List<RedCategoryTag> tags;

    private Date gmtCreate;
}
