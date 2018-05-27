package com.sumscope.security.spring_security.controller;

import com.sumscope.security.spring_security.model.ResponseTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuejian.sun
 * @date 2018/3/22
 */
@RestController
@RequestMapping(produces = {"application/json"})
public class HelloSecurity {

    @GetMapping("/my-info")
    public Authentication info(Authentication authentication){
        return authentication;
    }

    @GetMapping(value = "/hello")
    public String index(){
        return "hello security";
    }

    @RequestMapping("/session/expired")
    public ResponseTemplate sessionInvalid(){
        return new ResponseTemplate("会话已经过期，请重新登录！");
    }

    @GetMapping("/vip")
    public ResponseTemplate vip(){
        return new ResponseTemplate("当前页面只有VIP可见！");
    }
}
