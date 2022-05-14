package com.redskt.classroom.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RedMessageReplyVo {

    private String id;

    private String rid;

    private String content;

    private int good;

    private String uid;

    private boolean goodreply;

    private String name;

    private String touid;

    private String toname;

    private boolean showeditor;

    private String avatar;

    private Date gmtCreate;
}
