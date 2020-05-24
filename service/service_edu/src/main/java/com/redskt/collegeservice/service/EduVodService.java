package com.redskt.collegeservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EduVodService {
    //上传视频到阿里云
    String uploadVideoAly(MultipartFile file);

    //删除多个阿里云视频的方法
    void removeMoreAlyVideo(List videoIdList);
}
