package com.codej.springbootinit.config;

import com.codej.springbootinit.utils.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头中的token
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = JwtUtil.extractUsername(jwt);
        }

        // 验证token
        if (username != null && JwtUtil.validateToken(jwt, username)) {
            return true;  // Token有效，允许请求
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Token无效，返回401
        return false;
    }
}
