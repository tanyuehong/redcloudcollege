package com.redskt.classroom.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RedAskReplyVo {

    private String id;

    private String uid;

    private String qid;

    private String content;

    private Integer good;

    private Integer bad;

    private Integer myreply;

    private boolean showeditor;

    private String username;

    private String avatar;

    private Date gmtCreate;
}
