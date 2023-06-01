package com.redskt.classroom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redskt.classroom.entity.RedCategoryTag;
import com.redskt.classroom.entity.admin.vo.RedInterviewPositionTagsVo;
import com.redskt.classroom.entity.vo.RedClassAskQuestionVo;
import com.redskt.classroom.service.RedCategoryTagService;
import com.redskt.classroom.service.RedUserService;
import com.redskt.commonutils.R;
import com.redskt.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class RedTagsUserController {

    @Autowired
    private RedCategoryTagService tagService;

    @Autowired
    private RedUserService userService;

    @PostMapping("addTag")
    public R addTag(@RequestBody RedCategoryTag tag, HttpServletRequest request) {
        String uId = TokenManager.getMemberIdByJwtToken(request);
        if (tag.getName().length() > 0 && userService.checkIsAdmin(uId)) {
            if(tag.getId() != null && tag.getId().length()>0) {
                if(tagService.updateById(tag)) {
                    return R.okSucessTips("修改标签成功~");
                }
            } else {
                QueryWrapper<RedCategoryTag> tagQueryWrapper = new QueryWrapper<>();
                tagQueryWrapper.eq("name", tag.getName());
                RedCategoryTag oldTag = tagService.getOne(tagQueryWrapper);
                if (oldTag != null) {
                    return R.error("该标签已经存在，不要反复创建哦~");
                }
                if (tagService.save(tag)) {
                    return R.okSucessTips("添加新标签成功~");
                }
            }
            return R.error("操作失败，请重新尝试！");
        } else {
            return R.errorParam();
        }
    }

    @GetMapping("deleteTag/{tId}")
    public R deleteTag(@PathVariable String tId) {
        if(tId.length()>0) {
            if (tagService.removeById(tId)) {
                return R.okSucessTips("删除成功！");
            } else {
                return R.error("操作异常，请重新操作哦~");
            }
        } else {
            return R.errorParam();
        }
    }

    @GetMapping("getTagList")
    public R getAskTagList() {
        QueryWrapper<RedCategoryTag> tagQueryWrapper = new QueryWrapper<>();
        tagQueryWrapper.orderByAsc("gmt_create");
        List<RedCategoryTag> tagList = tagService.list(tagQueryWrapper);
        return R.ok().data("tagList",tagList);
    }

}
