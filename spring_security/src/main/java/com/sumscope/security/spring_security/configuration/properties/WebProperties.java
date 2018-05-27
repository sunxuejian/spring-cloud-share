package com.sumscope.security.spring_security.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xuejian.sun
 * @date 2018/5/27
 */
@Data
@ConfigurationProperties(prefix = "stc.security.web")
public class WebProperties {
    /**
     * 自定义登录页面
     */
    private String loginPage = "/login-default.html";

    private LoginType loginType = LoginType.JSON;
}
