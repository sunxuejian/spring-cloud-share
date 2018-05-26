package com.sumscope.security.spring_security.configuration;

import com.sumscope.security.spring_security.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author xuejian.sun
 * @date 2018/5/26
 */
@Configuration
public class ApplicationConfiguration {

    @Bean(initMethod = "initialize")
    public UserDetailsService userService(){
        return new UserService();
    }

    /**
     * 加密用户登录信息
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
