package com.songheqing.microforum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.songheqing.microforum.interceptor.TokenInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 拦截器对象
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 匹配所有接口
                .allowedOriginPatterns("http://localhost:*") // 明确允许的域名 1
                .allowedMethods("*") // 允许所有HTTP方法
                .allowedHeaders("Content-Type", "Authorization", "X-Requested-With") // 限制请求头
                .allowCredentials(true) // 允许凭证
                .maxAge(3600); // 预检请求缓存时间（秒），减少OPTIONS请求频率
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册自定义拦截器对象
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns(
                        "/login", // 排除登录请求
                        "/swagger-ui/**", // 排除Swagger请求
                        "/v3/api-docs/**", // 排除Swagger请求
                        // "/swagger-resources/**", // 排除Swagger请求
                        // "/webjars/**", // 排除Swagger请求
                        // "/v3/api-docs-ext/**", // 排除Swagger请求
                        // "/configuration/ui", // 排除Swagger请求
                        // "/configuration/security", // 排除Swagger请求
                        // "/swagger-ui.html", // 排除Swagger请求
                        "/favicon.ico" // <--- 新增，放行网站图标
                // "/articles" // 排除文章请求
                );
    }
}