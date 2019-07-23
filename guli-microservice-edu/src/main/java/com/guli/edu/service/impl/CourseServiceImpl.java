package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.constants.ResultCodeEnum;
import com.guli.common.exception.GuliException;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.CourseDescription;
import com.guli.edu.form.CourseInfoForm;
import com.guli.edu.mapper.CourseDescriptionMapper;
import com.guli.edu.mapper.CourseMapper;
import com.guli.edu.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
