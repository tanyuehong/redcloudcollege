package com.redskt.classroom.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//一级分类
@Data

public class RedClassSubjectOneVo {
    private String id;
    private String title;

    //一个一级分类有多个二级分类
    private List<RedClassSubjectTwoVo> children = new ArrayList<>();
}
