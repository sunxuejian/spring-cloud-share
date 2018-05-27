package com.sumscope.security.spring_security.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xuejian.sun
 * @date 2018/5/27
 */
public class StcExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    /**
     * 该方法会监听到session过期，可以在用户被异地踢出时给一些提示
     * @param event
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException{
        HttpServletResponse response = event.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("该用户在别的地方已经登录！");
    }
}
