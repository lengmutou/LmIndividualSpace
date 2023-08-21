package com.lengmu.handle;

import com.alibaba.fastjson2.JSONObject;
import com.lengmu.util.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Result failed = Result.failed("你没有权限，前面的区域以后再来探索吧");
        String failedResult = JSONObject.toJSONString(failed);
        response.getWriter().write(failedResult);
    }
}
