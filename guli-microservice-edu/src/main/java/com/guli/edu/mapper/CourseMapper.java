package com.guli.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.edu.entity.Course;
import com.guli.edu.vo.CoursePublishVo;
import com.guli.edu.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author changchen
 * @since 2019-07-12
 */
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo selectCoursePublishVoById(String id);

    //根据id查询课程
    CourseWebVo selectCourseWebVoById(String courseId);
}
