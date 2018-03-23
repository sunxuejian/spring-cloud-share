package com.sumscope.security.spring_security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xuejian.sun
 * @date 2018/3/23
 */
@Configuration
public class WebMvcConfiguration{

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        System.out.println("跳转一下--------------------------------");
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/login").setViewName("login");
            }
        };
    }
}
