package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.Subject;
import com.guli.edu.vo.SubjectNestedVo;
import com.guli.edu.vo.SubjectVo2;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author changchen
 * @since 2019-07-12
 */
public interface SubjectService extends IService<Subject> {

    //批量导入Excel数据
    List<String> batchImport(MultipartFile file) throws Exception;

    //封装一级、二级嵌套数据
    List<SubjectNestedVo> nestedList();

    //方式二
    List<SubjectVo2> nestedList2();

}
