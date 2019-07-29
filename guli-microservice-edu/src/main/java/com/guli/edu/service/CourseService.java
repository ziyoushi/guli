package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.Course;
import com.guli.edu.form.CourseInfoForm;
import com.guli.edu.query.CourseQuery;
import com.guli.edu.vo.CoursePublishVo;
import com.guli.edu.vo.CourseWebVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author changchen
 * @since 2019-07-12
 */
public interface CourseService extends IService<Course> {

    //保存课程和课程详细信息
    String saveCourseInfo(CourseInfoForm courseInfoForm);

    //根据id数据回显
    CourseInfoForm getCourseInfoFormById(String id);

    //修改课程信息
    void updateCourseInfoById(CourseInfoForm courseInfoForm);

    //分页查询
    void pageQuery(Page<Course> pageParam, CourseQuery courseQuery);

    //根据id删除课程
    void removeCourseById(String id);

    //根据id获取已经发布的课程
    CoursePublishVo getCoursePublishVoById(String id);

    //根据id发布课程
    void publishCourseById(String id);

    //课程列表分页查询
    Map<String, Object> pageListWeb(Page<Course> pageParam);

    //根据id查询课程信息
    CourseWebVo selectCourseWebVoById(String id);

    //根据讲师id查询讲师所讲课程列表
    List<Course> selectByTeacherId(String teacherId);
}
