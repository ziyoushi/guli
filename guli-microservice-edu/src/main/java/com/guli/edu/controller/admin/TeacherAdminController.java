package com.guli.edu.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.vo.R;
import com.guli.edu.entity.Teacher;
import com.guli.edu.query.TeacherQuery;
import com.guli.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    public R list(){
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    //逻辑删teacher
    @ApiOperation("根据id删除讲师")
    @DeleteMapping("{id}")
    public R deleteById(@PathVariable String id){
        teacherService.removeById(id);
        return R.ok().message("删除成功");
    }

    //分页查询
    @ApiOperation("分页查询讲师")
    @GetMapping("{pageNum}/{pageSize}")
    public R pageList(
            @ApiParam(name = "pageNum", value = "当前页码", required = true)
            @PathVariable Long pageNum,
            @ApiParam(name = "pageSize", value = "每页记录数", required = true)
            @PathVariable Long pageSize,
            @ApiParam(name = "teacherQuery", value = "查询条件", required = false)
                    TeacherQuery teacherQuery){

        Page<Teacher> page = new Page<>(pageNum,pageSize);
//        teacherService.page(page,null);

        teacherService.pageQuery(page,teacherQuery);
        List<Teacher> records = page.getRecords();
        long total = page.getTotal();

        return R.ok().data("rows",records).data("total",total);
    }

}
