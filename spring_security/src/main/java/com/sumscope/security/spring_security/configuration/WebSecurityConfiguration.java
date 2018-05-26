package com.sumscope.security.spring_security.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author xuejian.sun
 * @date 2018/3/22
 */
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin() //基于表单的提交验证
                .loginPage("/sing-in.html") //指定我们自己的登录页面,
                .loginProcessingUrl("/my_login")
                .and()
                .authorizeRequests() //对任何请求进行权限控制
                .antMatchers("/sing-in.html","/*.css","/image/**").permitAll()//对我们的自定义的登录页面以及静态资源放行
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable(); //暂时关闭跨站防护
    }
}
