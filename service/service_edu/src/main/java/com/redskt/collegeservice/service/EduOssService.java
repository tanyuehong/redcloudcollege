package com.redskt.collegeservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface EduOssService {
    //上传头像到oss
    String uploadFileAvatar(MultipartFile file);
}
