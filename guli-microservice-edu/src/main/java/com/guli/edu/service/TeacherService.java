package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.Teacher;
import com.guli.edu.query.TeacherQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author changchen
 * @since 2019-07-12
 */
public interface TeacherService extends IService<Teacher> {

    void pageQuery(Page<Teacher> pageParam, TeacherQuery teacherQuery);

    //没有参数 只查询所有的teacher名和id
    List<Teacher> getAllTeacherName();

    //讲师分页查询
    Map<String, Object> pageListWeb(Page<Teacher> pageParam);
}
