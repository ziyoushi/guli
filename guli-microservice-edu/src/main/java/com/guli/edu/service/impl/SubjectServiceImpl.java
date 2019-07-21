package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.util.ExcelImportUtil;
import com.guli.edu.entity.Subject;
import com.guli.edu.mapper.SubjectMapper;
import com.guli.edu.service.SubjectService;
import com.guli.edu.vo.SubjectNestedVo;
import com.guli.edu.vo.SubjectVo;
import com.guli.edu.vo.SubjectVo2;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author changchen
 * @since 2019-07-12
 */
@Service
@Transactional
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public List<String> batchImport(MultipartFile file) throws Exception {

        //错误消息列表
        List<String> errorMsg = new ArrayList<>();
        //使用工具
        ExcelImportUtil excelImportUtil = new ExcelImportUtil(file.getInputStream());

        //获取工具表
        HSSFSheet sheet = excelImportUtil.getSheet();

        //获取物理行数
        int rowCount = sheet.getPhysicalNumberOfRows();
        if (rowCount <=1){
            errorMsg.add("请添加数据");
            return errorMsg;
        }

        for (int rowNum = 1; rowNum < rowCount; rowNum++) {

            //获取行数据
            HSSFRow rowData = sheet.getRow(rowNum);

            if (rowData != null){
                //获取一级分类
                String levelOneCellValue = "";
                //获取第一列
                HSSFCell cell = rowData.getCell(0);
                if (cell != null){
                    levelOneCellValue = excelImportUtil.getCellValue(cell);
                    if (StringUtils.isEmpty(levelOneCellValue)){
                        errorMsg.add("第"+rowNum+"行一级分类为空");
                        continue;
                    }
                }

                //todo 判断一级分类是否重复
                Subject subject = this.getByTitle(levelOneCellValue);
                String parentId = null;
                //如果subjectLevelOne为null说明没有重复的课程分类
                if (subject == null){
                    // 将一级分类存入数据库
                    Subject subjectLevelOne = new Subject();
                    subjectLevelOne.setTitle(levelOneCellValue);
                    subjectLevelOne.setSort(rowNum);
                    subjectMapper.insert(subjectLevelOne);
                    //返回id作为二级分类的parentId
                    parentId = subjectLevelOne.getId();
                }else {

                    parentId = subject.getId();
                }

                //获取二级分类 TODO
                String levelTwoCellValue = "";
                //获取第二列
                HSSFCell cell1 = rowData.getCell(1);
                if (cell1 != null){

                    levelTwoCellValue = excelImportUtil.getCellValue(cell1);
                    if (StringUtils.isEmpty(levelTwoCellValue)){
                        errorMsg.add("第"+rowNum+"行二级分类为空");
                        continue;
                    }
                }

                //判断二级分类是否重复 TODO
                Subject subSubject = this.getSubByTitle(levelTwoCellValue,parentId);
                if (subSubject == null){
                    //将二级分类存入数据库 TODO
                    //说明没有重复的 可以添加到数据库中
                    Subject subjectTwo = new Subject();
                    subjectTwo.setParentId(parentId);
                    subjectTwo.setTitle(levelTwoCellValue);
                    subjectTwo.setSort(rowNum);
                    subjectMapper.insert(subjectTwo);
                }

            }

        }

        return errorMsg;
    }

    @Override
    public List<SubjectNestedVo> nestedList() {

        List<SubjectNestedVo> subjectNestedVoArrayList = new ArrayList<>();

        //注意为了方便 组装数据 两次查询的排序方式一定要相同
        //只分两次查询 一次是将一级分类查出 一次是将二级分类查出后 进行数据封装

        //一级查询添加 parenId = 0
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id","0");
        queryWrapper.orderByAsc("sort","id");
        List<Subject> subjectList = baseMapper.selectList(queryWrapper);

        //添加二级查询 parentId != 0
        QueryWrapper<Subject> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.ne("parent_id","0");
        queryWrapper2.orderByAsc("sort","id");
        List<Subject> subSubjectList = baseMapper.selectList(queryWrapper2);


        //创建一级分类vo对象 将数据填充
        for (int i = 0; i < subjectList.size(); i++) {

            Subject subject = subjectList.get(i);
            SubjectNestedVo subjectNestedVo = new SubjectNestedVo();
            //对象拷贝
            BeanUtils.copyProperties(subject,subjectNestedVo);
            subjectNestedVoArrayList.add(subjectNestedVo);

            List<SubjectVo> subjectVoList = new ArrayList<>();

            for (int j = 0; j < subSubjectList.size(); j++) {
                Subject subSubject = subSubjectList.get(j);

                if (subSubject.getParentId().equals(subject.getId())){
                    SubjectVo subjectVo = new SubjectVo();
                    BeanUtils.copyProperties(subSubject,subjectVo);
                    subjectVoList.add(subjectVo);
                }

            }
            subjectNestedVo.setChildren(subjectVoList);
        }

        return subjectNestedVoArrayList;
    }

    //ps 这边主要是为了测试mapper.xml无法打包
    @Override
    public List<SubjectVo2> nestedList2() {
        return baseMapper.selectNestedListByParentId("0");
    }

    //判断一级分类是否重复
    private Subject getByTitle(String title){

        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",title);
        queryWrapper.eq("parent_id","0");

        return baseMapper.selectOne(queryWrapper);
    }

    //根据title parentId 查看二级分类是否重复
    private Subject getSubByTitle(String title,String parentId){

        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",title);
        queryWrapper.eq("parent_id",parentId);

        return baseMapper.selectOne(queryWrapper);
    }






}
