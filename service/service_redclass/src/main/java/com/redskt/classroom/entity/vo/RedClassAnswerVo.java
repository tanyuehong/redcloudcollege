package com.redskt.classroom.entity.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RedClassAnswerVo {

    private String id;

    private String uid;

    private String qid;

    private String content;

    private List<ReplyCommentVo> comments;

    private Integer view;

    private Integer good;

    private boolean isgood;

    private boolean iscollect;

    private Integer bad;

    private int state;

    private boolean goodreply;

    private boolean badreply;

    private Integer myreply;

    private boolean showeditor;

    private String username;

    private String avatar;

    private Date gmtCreate;
}
