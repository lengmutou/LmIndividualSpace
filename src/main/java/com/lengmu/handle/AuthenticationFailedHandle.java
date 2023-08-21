package com.lengmu.handle;

import com.alibaba.fastjson2.JSONObject;
import com.lengmu.util.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AuthenticationFailedHandle implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Result failed = Result.failed("身份验证错误，请重新登陆！");
        String failedResult = JSONObject.toJSONString(failed);
        response.getWriter().write(failedResult);
    }
}
