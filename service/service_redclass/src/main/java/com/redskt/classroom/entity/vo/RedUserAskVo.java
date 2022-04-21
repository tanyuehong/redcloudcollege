package com.redskt.classroom.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class RedUserAskVo {

    private float qMoney;  // 问答余额

    private Integer aDopt; // 被采纳

    private Integer qCount;  //  问答数量

    private Integer qGood;   //  被点赞

    private Integer qAnswer;   //  回答数量

    private Integer cComment;  //  评论数量

}
