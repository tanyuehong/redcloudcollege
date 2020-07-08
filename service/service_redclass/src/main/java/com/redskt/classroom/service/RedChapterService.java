package com.redskt.classroom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redskt.classroom.entity.RedClassChapter;
import com.redskt.classroom.entity.vo.RedClassChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-05
 */
public interface RedChapterService extends IService<RedClassChapter> {

    List<RedClassChapterVo> getChapterVideoByCourseId(String courseId);
}
