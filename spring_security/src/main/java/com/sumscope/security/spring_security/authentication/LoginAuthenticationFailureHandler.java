package com.sumscope.security.spring_security.authentication;

import com.sumscope.security.spring_security.Utils.JsonUtil;
import com.sumscope.security.spring_security.configuration.properties.LoginType;
import com.sumscope.security.spring_security.configuration.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**自定义登录失败处理器
 * @author xuejian.sun
 * @date 2018/5/27
 */
@Slf4j
public class LoginAuthenticationFailureHandler extends ExceptionMappingAuthenticationFailureHandler {

    private SecurityProperties securityProperties;

    public LoginAuthenticationFailureHandler(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    /**
     * 用户认证失败后，返回json信息，或者使用spring默认的异常处理机制处理。
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "";
        log.info("登录失败..");
        if(LoginType.JSON == securityProperties.getWebProperties().getLoginType()){
            if(exception instanceof BadCredentialsException){
                errorMessage = "密码错误,请重新输入";
            }else if(exception instanceof UsernameNotFoundException){
                errorMessage = "用户名不对，请重新输入";
            }else {
                errorMessage = exception.getMessage();
            }
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(errorMessage+":"+JsonUtil.writeValueAsString(exception));
        }else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
