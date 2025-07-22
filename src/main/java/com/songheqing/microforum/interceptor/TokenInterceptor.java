package com.songheqing.microforum.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.songheqing.microforum.utils.CurrentHolder;
import com.songheqing.microforum.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 0. 放行所有OPTIONS预检请求（必须放在最前面！）
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // 放行GET /articles（文章列表查询）
        if ("GET".equalsIgnoreCase(request.getMethod()) &&
                "/articles".equals(request.getRequestURI())) {
            return true;
        }

        // 放行GET /articles/{id}（文章详情查询）
        if ("GET".equalsIgnoreCase(request.getMethod()) &&
                "/articles/{id}".equals(request.getRequestURI())) {
            return true;
        }

        // 1. 获取请求url。
        // String url = request.getRequestURL().toString();

        // 2. 判断请求url中是否包含login，如果包含，说明是登录操作，放行。
        // if (url.contains("login")) { // 登录请求
        // log.info("登录请求 , 直接放行");
        // return true;
        // }

        // 3. 获取请求头中的令牌（token）。
        String jwt = request.getHeader("Authorization");

        // 4. 判断令牌是否存在，如果不存在，返回错误结果（未登录）。
        if (!StringUtils.hasLength(jwt)) { // jwt为空
            log.info("获取到jwt令牌为空, 返回错误结果");
            response.setStatus(401);
            return false;
        }

        // 5. 解析token，如果解析失败，返回错误结果（未登录）。
        try {
            Claims claims = jwtUtil.parseToken(jwt);
            Integer userId = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("解析令牌失败, 返回错误结果");
            response.setStatus(401);
            return false;
        }

        // 6. 放行。
        log.info("令牌合法, 放行");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 7. 清空当前线程绑定的id
        CurrentHolder.remove();
    }
}
