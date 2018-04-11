package com.sumscope.example.easy_excel;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;
import com.sumscope.example.easy_excel.model.User;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xuejian.sun
 * @date 2018/4/11
 */
@Slf4j
public class ValidationHandler extends ExcelDataHandlerDefaultImpl<User> {

    public ValidationHandler() {
        setNeedHandlerFields(new String[]{"姓名","户口所在地"});
    }

    @Override
    public Object importHandler(User obj, String name, Object value) {
        log.info("{}:{}",name,value);
        if(value != null){
            return value;
        }
        obj = null;
        return obj;
    }
}
