package com.redskt.classroom.entity.vo;

import com.redskt.classroom.entity.RedAskReplyComment;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RedAskReplyVo {

    private String id;

    private String uid;

    private String qid;

    private String content;

    private List<RedAskReplyComment> comments;

    private Integer good;

    private Integer bad;

    private boolean goodreply;

    private boolean badreply;

    private Integer myreply;

    private boolean showeditor;

    private String username;

    private String avatar;

    private Date gmtCreate;
}
