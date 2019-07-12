package com.guli.edu.controller.admin;


import com.guli.edu.entity.Teacher;
import com.guli.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author changchen
 * @since 2019-07-12
 */
@CrossOrigin //跨域
@RestController
@RequestMapping("/admin/edu/teacher")
public class TeacherAdminController {

    @Autowired
    private TeacherService teacherService;

    //查询teacher
    @GetMapping
    public List<Teacher> list(){
        return teacherService.list(null);
    }

    //逻辑删teacher
    @DeleteMapping("{id}")
    public boolean deleteById(@PathVariable String id){
        return teacherService.removeById(id);
    }

}
