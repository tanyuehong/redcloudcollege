package com.redskt.classroom.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class RedUserAskVo {

    private String id;

    private float qMoney;  // 问答余额

    private int aDopt; // 被采纳

    private int qCount;  //  问答数量

    private int qGood;   //  被点赞

    private int rGood;   //  被点赞

    private int cGood;   //  被点赞

    private int qAnswer;   //  回答数量

    private int cComment;  //  回答回复数量

    private int crComment;  //  评论回复数量

}
