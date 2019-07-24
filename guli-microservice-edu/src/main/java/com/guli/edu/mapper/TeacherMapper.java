package com.guli.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.edu.entity.Teacher;

import java.util.List;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author changchen
 * @since 2019-07-12
 */
public interface TeacherMapper extends BaseMapper<Teacher> {

    List<Teacher> getAllTeacherName();
}
