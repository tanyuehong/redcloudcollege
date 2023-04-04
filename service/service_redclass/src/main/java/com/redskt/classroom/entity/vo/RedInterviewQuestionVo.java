package com.redskt.classroom.entity.vo;

import com.redskt.classroom.entity.RedCategoryTag;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RedInterviewQuestionVo {

    private String qId;

    private String pId;

    private String uId;

    private String nickname;

    private String avatar;

    private String sNum;

    private String title;

    private String type;

    private String deep;

    private String typeString;

    private String subString;

    private String content;

    private String answer;

    private Integer reply;

    private Integer readCount;

    private Integer collect;

    private Integer good;

    private Integer price;

    private Integer hotnum;

    private int allMeet;   // 遇到次数

    private float frequency;

    private List<RedCategoryTag> tags;

    private Date gmtCreate;
}
