package com.guli.edu.controller.admin;


import com.guli.edu.entity.Teacher;
import com.guli.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "讲师管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/admin/edu/teacher")
public class TeacherAdminController {

    @Autowired
    private TeacherService teacherService;

    //查询teacher
    @ApiOperation("查询所有讲师列表")
    @GetMapping
    public List<Teacher> list(){
        return teacherService.list(null);
    }

    //逻辑删teacher
    @ApiOperation("根据id删除讲师")
    @DeleteMapping("{id}")
    public boolean deleteById(@PathVariable String id){
        return teacherService.removeById(id);
    }

}
