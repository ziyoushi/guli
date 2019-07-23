package com.guli.edu.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.constants.ResultCodeEnum;
import com.guli.common.exception.GuliException;
import com.guli.common.vo.R;
import com.guli.edu.entity.Teacher;
import com.guli.edu.entity.TeacherVo;
import com.guli.edu.query.TeacherQuery;
import com.guli.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

        if (pageNum<=0 || pageSize <=0){
            throw new GuliException(ResultCodeEnum.PARAM_ERROR);
        }

        Page<Teacher> page = new Page<>(pageNum,pageSize);
//        teacherService.page(page,null);

        teacherService.pageQuery(page,teacherQuery);
        List<Teacher> records = page.getRecords();
        long total = page.getTotal();

        return R.ok().data("rows",records).data("total",total);
    }

    //查询teacher 无参数 只需要返回 id，name
    @ApiOperation("查询讲师")
    @GetMapping("/getAll")
    public R getTeacherList(){

        List<Teacher> records = teacherService.getAllTeacherName();
        List<TeacherVo> recordVo = new ArrayList<>();
        for (Teacher teacher : records) {
            TeacherVo teacherVo = new TeacherVo();
            BeanUtils.copyProperties(teacher,teacherVo);
            recordVo.add(teacherVo);
        }

        return R.ok().data("rows",recordVo);
    }


    //新增讲师
    @ApiOperation("新增讲师")
    @PostMapping("/add")
    public R save(@RequestBody Teacher teacher){

        teacherService.save(teacher);
        return R.ok();
    }

    //根据id查询讲师
    @GetMapping("{id}")
    public R getTeacherById(@PathVariable String id){

        Teacher teacher = teacherService.getById(id);

        return R.ok().message("查询成功").data("item",teacher);
    }

    //根据id修改讲师
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id,
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher){

        teacher.setId(id);
        teacherService.updateById(teacher);

        return R.ok().message("修改成功");
    }

}
