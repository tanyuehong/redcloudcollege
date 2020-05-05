package com.redskt.collegeservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.redskt.collegeservice.entity.EduSubject;
import com.redskt.collegeservice.entity.excel.SubjectData;
import com.redskt.collegeservice.listener.SubjectExcelListener;
import com.redskt.collegeservice.mapper.EduSubjectMapper;
import com.redskt.collegeservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-05
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
