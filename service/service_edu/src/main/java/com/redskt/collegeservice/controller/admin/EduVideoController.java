package com.redskt.collegeservice.controller.admin;


import com.redskt.collegeservice.entity.EduVideo;
import com.redskt.collegeservice.service.EduVideoService;
import com.redskt.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2020-05-05
 */
@RestController
@RequestMapping("/collegeservice/video")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    // TODO 后面这个方法需要完善：删除小节时候，同时把里面视频删除
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        videoService.removeById(id);
        return R.ok();
    }

    //修改小节 TODO
}

