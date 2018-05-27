package com.sumscope.security.spring_security.configuration.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuejian.sun
 * @date 2018/5/27
 */
@Getter
@Configuration
@EnableConfigurationProperties(WebProperties.class)
@ConfigurationProperties(prefix = "stc.security")
public class SecurityProperties {
    @Autowired
    private WebProperties webProperties;
}
