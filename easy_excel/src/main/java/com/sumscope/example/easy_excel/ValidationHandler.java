package com.sumscope.example.easy_excel;

import com.sumscope.example.easy_excel.model.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jeecgframework.poi.handler.impl.ExcelDataHandlerDefaultImpl;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xuejian.sun
 * @date 2018/4/11
 */
@Slf4j
public class ValidationHandler extends ExcelDataHandlerDefaultImpl<User> {
    @Getter
    private List<String> errorMsg;

    public ValidationHandler() {
        setNeedHandlerFields(new String[]{"户口所在地"});
        errorMsg = new LinkedList<>();
    }

    @Override
    public Object importHandler(User obj, String name, Object value) {
        log.info("{}:{}",name,value);
        if(!value.equals("上海")){
            errorMsg.add(obj.getName()+"非上海人!");
        }
        return obj;
    }
}
