package com.redskt.redoss.service;

import org.springframework.web.multipart.MultipartFile;

public interface RedOssService {
    //上传头像到oss
    String uploadFileAvatar(MultipartFile file);
}
