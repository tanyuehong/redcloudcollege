package com.redskt.classroom.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RedClassChapterVo {
    private String id;

    private String title;

    //表示小节
    private List<RedClassVideoVo> children = new ArrayList<>();
}
