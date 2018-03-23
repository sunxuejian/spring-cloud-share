package com.sumscope.security.spring_security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuejian.sun
 * @date 2018/3/22
 */
@RestController
@RequestMapping(produces = {"application/json"})
public class HelloSecurity {

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }
}
