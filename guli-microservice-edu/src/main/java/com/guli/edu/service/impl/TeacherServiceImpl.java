package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.edu.entity.Teacher;
import com.guli.edu.mapper.TeacherMapper;
import com.guli.edu.query.TeacherQuery;
import com.guli.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author changchen
 * @since 2019-07-12
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public void pageQuery(Page<Teacher> pageParam, TeacherQuery teacherQuery) {

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        if (teacherQuery == null){
            baseMapper.selectPage(pageParam,queryWrapper);
            return;
        }

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }

        if (!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }

        if (!StringUtils.isEmpty(begin)){
            queryWrapper.ge("begin",begin);
        }

        if (!StringUtils.isEmpty(end)){
            queryWrapper.le("end",end);
        }

        baseMapper.selectPage(pageParam,queryWrapper);

    }

    @Override
    public List<Teacher> getAllTeacherName() {
        return teacherMapper.getAllTeacherName();
    }

    @Override
    public Map<String, Object> pageListWeb(Page<Teacher> pageParam) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        baseMapper.selectPage(pageParam, queryWrapper);

        Map<String, Object> map = new HashMap<>();

        List<Teacher> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}
