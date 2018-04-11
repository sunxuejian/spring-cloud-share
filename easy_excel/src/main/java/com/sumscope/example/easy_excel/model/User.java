package com.sumscope.example.easy_excel.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * @author xuejian.sun
 * @date 2018/4/11
 */
@Data
public class User {
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "年龄")
    @Max(value = 20)
    private String age;
    @NotBlank
    @Excel(name = "电话")
    private String phoneNumber;
    @Excel(name = "户口所在地")
    private String originAddress;
    @Excel(name = "目前居住地")
    private String currentAddress;
}
