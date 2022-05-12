package com.redskt.classroom.entity.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RedMessageCommentVo {
    private String id;

    private String uid;

    private String bid;

    private String content;

    private List<ReplyCommentVo> comments;

    private Integer good;

    private boolean goodreply;

    private boolean badreply;

    private Integer myreply;

    private boolean showeditor;

    private String username;

    private String avatar;

    private Date gmtCreate;
}
