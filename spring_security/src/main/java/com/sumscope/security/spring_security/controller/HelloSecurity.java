package com.sumscope.security.spring_security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuejian.sun
 * @date 2018/3/22
 */
@RestController
//@RequestMapping(produces = {"application/json"})
public class HelloSecurity {

//    @PostMapping("/sing2")
//    public String login(){
//        return "hello";
//    }

    @RequestMapping(value = "/hello")
    public String index(){
        return "hello security";
    }
}
