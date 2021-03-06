package com.redskt.collegeservice.service;

import com.redskt.collegeservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.collegeservice.entity.subject.SubjectOne;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-05
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    List<SubjectOne> getAllOneTwoSubject();
}
