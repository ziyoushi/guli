package com.guli.edu.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @create 2019-07-21 14:40
 * 嵌套查询 方式二
 */
@Data
public class SubjectVo2 {
    private String id;
    private String title;
    private List<SubjectVo2> children = new ArrayList<>();
}
