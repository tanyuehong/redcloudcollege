package com.redskt.classroom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.RedClassSubject;
import com.redskt.classroom.entity.vo.RedClassSubjectOneVo;
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
public interface RedSubjectService extends IService<RedClassSubject> {

    List<RedClassSubjectOneVo> getAllOneTwoSubject();
}
