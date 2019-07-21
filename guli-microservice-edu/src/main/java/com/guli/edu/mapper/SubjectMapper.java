package com.guli.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.edu.entity.Subject;
import com.guli.edu.vo.SubjectVo2;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author changchen
 * @since 2019-07-12
 */
public interface SubjectMapper extends BaseMapper<Subject> {

    List<SubjectVo2> selectNestedListByParentId(String parentId);
}
