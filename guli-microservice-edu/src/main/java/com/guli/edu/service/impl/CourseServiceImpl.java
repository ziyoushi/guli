package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.constants.ResultCodeEnum;
import com.guli.common.exception.GuliException;
import com.guli.edu.entity.Chapter;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.CourseDescription;
import com.guli.edu.entity.Video;
import com.guli.edu.form.CourseInfoForm;
import com.guli.edu.mapper.ChapterMapper;
import com.guli.edu.mapper.CourseDescriptionMapper;
import com.guli.edu.mapper.CourseMapper;
import com.guli.edu.mapper.VideoMapper;
import com.guli.edu.query.CourseQuery;
import com.guli.edu.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author changchen
 * @since 2019-07-12
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionMapper descriptionMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Transactional
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {

        //保存课程基本信息
        Course course = new Course();
        course.setStatus(Course.COURSE_DRAFT);
        BeanUtils.copyProperties(courseInfoForm,course);
        baseMapper.insert(course);

        //再保存到课程详情
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(course.getId());
        courseDescription.setDescription(courseInfoForm.getDescription());
        descriptionMapper.insert(courseDescription);

        //返回课程id
        return course.getId();
    }

    @Override
    public CourseInfoForm getCourseInfoFormById(String id) {

        //从course表中获取数据
        Course course = baseMapper.selectById(id);
        if(course == null){
            throw new GuliException(ResultCodeEnum.UNKNOWN_REASON);
        }

        //从course_description表中获取数据
        CourseDescription courseDescription = descriptionMapper.selectById(id);
        if (courseDescription == null){
            throw new GuliException(ResultCodeEnum.UNKNOWN_REASON);
        }

        //将数据封装换成CourseInfoForm对象返回
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course,courseInfoForm);
        BeanUtils.copyProperties(courseDescription,courseInfoForm);

        return courseInfoForm;
    }

    @Override
    public void updateCourseInfoById(CourseInfoForm courseInfoForm) {

        //注意 先改course 再改course_description
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm,course);
        baseMapper.updateById(course);

        CourseDescription courseDescription = new CourseDescription();
        BeanUtils.copyProperties(courseInfoForm,courseDescription);
        descriptionMapper.updateById(courseDescription);

    }

    //使用mp插件分页查询
    @Override
    public void pageQuery(Page<Course> pageParam, CourseQuery courseQuery) {

        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        //根据创建时间 倒序查询
        queryWrapper.orderByDesc("gmt_create");

        if (courseQuery == null){
            baseMapper.selectPage(pageParam,queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String subjectId = courseQuery.getSubjectId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String teacherId = courseQuery.getTeacherId();

        if (!StringUtils.isEmpty(title)){
            queryWrapper.eq("title",title);
        }

        if (!StringUtils.isEmpty(subjectId)){
            queryWrapper.eq("subject_id",subjectId);
        }

        if (!StringUtils.isEmpty(subjectParentId)){
            queryWrapper.eq("subject_parent_id",subjectParentId);
        }

        if (!StringUtils.isEmpty(teacherId)){
            queryWrapper.eq("teacher_id",teacherId);
        }

        baseMapper.selectPage(pageParam,queryWrapper);

    }

    @Override
    public void removeCourseById(String id) {
        //要删除课程 需要根据课程id-->即 course_id
        // 根据course_id 删除 video chapter
        QueryWrapper<Chapter> queryWrapperChapter = new QueryWrapper<>();
        queryWrapperChapter.eq("course_id",id);
        chapterMapper.delete(queryWrapperChapter);

        QueryWrapper<Video> queryWrapperVideo = new QueryWrapper<>();
        queryWrapperVideo.eq("course_id",id);
        videoMapper.delete(queryWrapperVideo);

        baseMapper.deleteById(id);
    }

}
