package com.sumscope.security.spring_security.configuration;

import com.sumscope.security.spring_security.authentication.LoginAuthenticationFailureHandler;
import com.sumscope.security.spring_security.authentication.LoginAuthenticationSuccessHandler;
import com.sumscope.security.spring_security.configuration.properties.SecurityProperties;
import com.sumscope.security.spring_security.session.StcExpiredSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

/**
 * @author xuejian.sun
 * @date 2018/3/22
 */
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * security中用户一旦登录就会把当前会话保存到 SecurityContextHolder中
     */
    private SecurityContextHolder securityContextHolder = new SecurityContextHolder();

    /**
     * spring security处理身份认证前会把当前请求
     * 缓存到这个容器中
     * @return
     */
    @Bean
    public RequestCache requestCache(){
        return new HttpSessionRequestCache();
    }

    /**
     * 注册自定义登录成功处理器
     * @return AuthenticationSuccessHandler
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new LoginAuthenticationSuccessHandler(securityProperties);
    }

    /**
     * 注册自定义登录失败处理器
     * @return ExceptionMappingAuthenticationFailureHandler
     */
    @Bean
    public ExceptionMappingAuthenticationFailureHandler exceptionMappingAuthenticationFailureHandler(){
        return new LoginAuthenticationFailureHandler(securityProperties);
    }


    /**
     * 配置一个用于跳转页面的工具
     * @return
     */
    @Bean
    public RedirectStrategy redirectStrategy(){
        return new DefaultRedirectStrategy();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin() //基于表单的提交验证
                .loginPage("/authentication/require") //指定我们自己的登录页面,
                .loginProcessingUrl("/my_login") //这里的配置需要和from表单提交的路径保持一致
                .successHandler(authenticationSuccessHandler())
                .failureHandler(exceptionMappingAuthenticationFailureHandler())
                .and()
                .sessionManagement()
                    .invalidSessionUrl("/session/expired")//session过期时访问的服务
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
                    .expiredSessionStrategy(new StcExpiredSessionStrategy()) //多个session登录时触发的策略
                .and()
                .and()
                .authorizeRequests() //对任何请求进行权限控制
                .antMatchers("/authentication/require",
                        securityProperties.getWebProperties().getLoginPage(),
                        "/*.css",
                        "/image/**",
                        "/session/expired"
                        )
                .permitAll()//对我们的自定义的登录页面以及静态资源放行
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable(); //暂时关闭跨站防护
    }
}
