package com.sumscope.example.easy_excel.model;

import cn.afterturn.easypoi.excel.annotation.ExcelCollection;

import java.util.List;

/**
 * @author xuejian.sun
 * @date 2018/4/11
 */
public class ManeyResult {
    @ExcelCollection(name="学生信息")
    private List<User> users;
}
