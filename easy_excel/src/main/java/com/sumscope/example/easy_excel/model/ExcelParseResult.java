package com.sumscope.example.easy_excel.model;


import lombok.Data;

import java.util.List;

/**
 * @author xuejian.sun
 * @date 2018/4/11
 */
@Data
public class ExcelParseResult<T> {

    private String sheetName;

    private List<T> result;

    private List<String> errorMsg;
}
