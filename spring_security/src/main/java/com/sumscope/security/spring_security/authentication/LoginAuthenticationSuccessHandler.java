package com.sumscope.security.spring_security.authentication;

import com.sumscope.security.spring_security.Utils.JsonUtil;
import com.sumscope.security.spring_security.configuration.properties.LoginType;
import com.sumscope.security.spring_security.configuration.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**自定义登录成功处理器
 * @author xuejian.sun
 * @date 2018/5/27
 */
@Slf4j
public class LoginAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private SecurityProperties securityProperties;

    public LoginAuthenticationSuccessHandler(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    /**
     * 登录成功后,根据配置文件来决定返回给前端的数据类型，默认返回json，否则使用spring默认的跳转机制
     * @param request
     * @param response
     * @param authentication
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.info("登录成功：{}",authentication);
        if( LoginType.JSON == securityProperties.getWebProperties().getLoginType()){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonUtil.writeValueAsString(authentication));
        }else{
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
