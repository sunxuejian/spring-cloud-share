package com.sumscope.example.easy_excel.model;

import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xuejian.sun
 * @date 2018/4/11
 */
@Slf4j
public class UserExcelListener extends AnalysisEventListener {

    @Override
    public void invoke(Object user, AnalysisContext analysisContext) {
        log.info("用户信息:{}",user);
        User user1 = (User) user;
        log.info("当前行号:{}",analysisContext.getCurrentRowNum());
        log.info("当前sheet名字:{}",analysisContext.getCurrentSheet().getSheetName());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("Excel处理完毕...");
    }
}
