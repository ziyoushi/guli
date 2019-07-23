package com.guli.edu.controller.admin;


import com.guli.common.vo.R;
import com.guli.edu.form.CourseInfoForm;
import com.guli.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 后端控制器
 * </p>
 *
 * @author changchen
 * @since 2019-07-12
 */
@Api(description="课程管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/admin/edu/course")
public class CourseAdminController {

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "新增课程")
    @PostMapping("save-course-info")
    public R saveCourseInfo(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm){

        String courseId = courseService.saveCourseInfo(courseInfoForm);
        return R.ok().data("courseId", courseId);
    }

    //根据id查询课程
    @ApiOperation(value = "根据ID查询课程")
    @GetMapping("/course-info/{id}")
    public R getCourseById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        CourseInfoForm courseInfoForm = courseService.getCourseInfoFormById(id);

        return R.ok().data("item",courseInfoForm);
    }

}
