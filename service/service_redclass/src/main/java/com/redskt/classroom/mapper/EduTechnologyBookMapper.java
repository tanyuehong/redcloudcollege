package com.redskt.classroom.mapper;

import com.redskt.classroom.entity.RedClassBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redskt.classroom.entity.vo.RedClassBookVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tanyuehong
 * @since 2021-02-28
 */
public interface EduTechnologyBookMapper extends BaseMapper<RedClassBook> {

   List<RedClassBookVo> getBookIndexInfo(@Param("size") int size);

}
