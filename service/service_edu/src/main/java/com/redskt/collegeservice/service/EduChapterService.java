package com.redskt.collegeservice.service;

import com.redskt.collegeservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.collegeservice.entity.subject.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-05
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
