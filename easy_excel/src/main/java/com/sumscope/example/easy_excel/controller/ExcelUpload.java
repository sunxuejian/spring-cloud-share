package com.sumscope.example.easy_excel.controller;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.io.BaseEncoding;
import com.sumscope.example.easy_excel.JsonUtil;
import com.sumscope.example.easy_excel.ValidationHandler;
import com.sumscope.example.easy_excel.model.ExcelParseResult;
import com.sumscope.example.easy_excel.model.User;
import com.sumscope.example.easy_excel.model.UserExcelListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.handler.impl.ExcelDataHandlerDefaultImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author xuejian.sun
 * @date 2018/4/11
 */
@Slf4j
@RestController
@RequestMapping(value = "/excel")
public class ExcelUpload {

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public void upload(@RequestBody String string) throws Exception {
        Map map = JsonUtil.readValue(string, Map.class);
        String fileName = (String)map.get("fileName");
        String fileContent = (String) map.get("excelContent");
        fileContent = fileContent.substring(fileContent.indexOf("base64,") + "base64,".length());
        byte[] bytes = BaseEncoding.base64().decode(fileContent);
        InputStream in = new ByteArrayInputStream(bytes);
        try {
            UserExcelListener userExcelListener = new UserExcelListener();

            ExcelReader reader = new ExcelReader(in,ExcelTypeEnum.XLSX,null,userExcelListener);

            reader.read();
        }catch (Exception e){

        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/uploadfile",method = RequestMethod.POST)
    public void uploadfile(@RequestParam("file") MultipartFile file) throws Exception {
        log.info("文件名:{}",file.getName());
        log.info("文件内容类型{}",file.getContentType());
        log.info("原文件名{}",file.getOriginalFilename());
        List<ExcelParseResult<User>> results = new LinkedList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        for(int i = 0; i < workbook.getNumberOfSheets(); i++) {
            ValidationHandler validationHandler = new ValidationHandler();
            ImportParams importParams = bySheet(i,validationHandler);
            ExcelImportResult<User> result = ExcelImportUtil.importExcelVerify(file.getInputStream(), User.class, importParams);
            ExcelParseResult<User> parseResult = new ExcelParseResult<>();
            parseResult.setErrorMsg(validationHandler.getErrorMsg());
            parseResult.setSheetName(workbook.getSheetName(i));
            parseResult.setResult(result.getList());
            results.add(parseResult);
        }
        workbook = null;
        results.forEach(result -> {
            log.info("sheetName:{}",result.getSheetName());
            result.getResult().forEach(user -> log.info("解析结果:{}",user));
            result.getErrorMsg().forEach(err -> log.info("错误信息:{}",err));
        });
    }


    private ImportParams bySheet(int startSheetIndex, ExcelDataHandlerDefaultImpl excelDataHandlerDefault) throws IOException {
        ImportParams importParams = new ImportParams();
        importParams.setDataHanlder(excelDataHandlerDefault);
        importParams.setNeedVerfiy(true);
//        importParams.setNeedSave(true);
        importParams.setStartSheetIndex(startSheetIndex);
        return importParams;
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public void get(){
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("easypoi.xlsx");
        try {
            UserExcelListener userExcelListener = new UserExcelListener();

            ExcelReader reader = new ExcelReader(in,ExcelTypeEnum.XLSX,null,userExcelListener);
            reader.getSheets().forEach(sheet -> reader.read(sheet));
        }catch (Exception e){

        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
