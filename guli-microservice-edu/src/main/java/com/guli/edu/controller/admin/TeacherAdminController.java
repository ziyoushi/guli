package com.guli.edu.controller.admin;


import com.guli.edu.entity.Teacher;
import com.guli.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author changchen
 * @since 2019-07-12
 */
@RestController
@RequestMapping("/admin/edu/teacher")
public class TeacherAdminController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public List<Teacher> list(){
        return teacherService.list(null);
    }

}

