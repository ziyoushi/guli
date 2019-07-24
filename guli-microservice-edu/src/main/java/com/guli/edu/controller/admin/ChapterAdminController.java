package com.guli.edu.controller.admin;


import com.guli.common.vo.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 课程章节 后端控制器
 * </p>
 *
 * @author changchen
 * @since 2019-07-12
 */
@RestController
@RequestMapping("/admin/edu/chapter")
public class ChapterAdminController {


    //新增课程章节
    @PostMapping
    public R addChapter(){

        return R.ok();
    }

    //嵌套查询课程章节列表


}

