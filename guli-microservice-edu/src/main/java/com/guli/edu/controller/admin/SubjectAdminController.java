package com.guli.edu.controller.admin;


import com.guli.common.constants.ResultCodeEnum;
import com.guli.common.exception.GuliException;
import com.guli.common.vo.R;
import com.guli.edu.service.SubjectService;
import com.guli.edu.vo.SubjectNestedVo;
import com.guli.edu.vo.SubjectVo2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author changchen
 * @since 2019-07-12
 */
@Api(value="课程分类管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/admin/edu/subject")
public class SubjectAdminController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation(value = "Excel批量导入")
    @PostMapping("/import")
    public R batchImport(@RequestParam("file") MultipartFile file){

        try {
            List<String> errorMsg = subjectService.batchImport(file);
            if (errorMsg.size() == 0){
                //批量导入成功
                return R.ok().message("批量导入成功");
            }else {
                return R.error().message("部门数据导入失败").data("errorMsgList",errorMsg);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }

    }

    //前端调用的是这个接口
    @ApiOperation(value = "嵌套数据列表")
    @GetMapping("")
    public R nestedList(){

        List<SubjectNestedVo> subjectNestedVoList = subjectService.nestedList();
        return R.ok().data("items", subjectNestedVoList);
    }

    @ApiOperation(value = "嵌套数据列表test")
    @GetMapping("list2")
    public R nestedList2(){

        List<SubjectVo2> subjectVo2List = subjectService.nestedList2();
        return R.ok().data("items", subjectVo2List);
    }

}

