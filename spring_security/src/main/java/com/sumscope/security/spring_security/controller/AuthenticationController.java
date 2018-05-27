package com.sumscope.security.spring_security.controller;

import com.sumscope.security.spring_security.configuration.properties.SecurityProperties;
import com.sumscope.security.spring_security.model.ResponseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xuejian.sun
 * @date 2018/5/27
 */
@Slf4j
@RestController
public class AuthenticationController {

    @Autowired
    private RequestCache requestCache;

    @Autowired
    private RedirectStrategy redirectStrategy;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 自定义身份认证控制器，请求身份认证时，跳转到此处
     * WebSecurityConfiguration 中需要对这个方法已经我们的跳转页面进行放行
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED) //未授权错误码
    public ResponseTemplate authentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /**
         * 获取到当前请求
         */
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if(savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            log.info("触发请求的地址：{}", redirectUrl);
            String method = savedRequest.getMethod();
            log.info("触发请求的方法：{}", method);
            /**
             * 如果发起请求的是html页面，就让他跳转到自定义的登录页面
             */
            if(StringUtils.endsWithIgnoreCase(redirectUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response,"/sing-in.html");
            }
        }
        return new ResponseTemplate("访问的服务需要授权,请重新登录!");
    }
}
