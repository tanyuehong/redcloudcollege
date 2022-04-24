package com.redskt.classroom.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RedBlogCommentReplyVo {
    private String id;

    private String rid;

    private String content;

    private String uid;

    private String name;

    private String touid;

    private String toname;

    private boolean showeditor;

    private String avatar;

    private int good;

    private int goodcomment;

    private Date gmtCreate;
}
