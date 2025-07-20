package com.songheqing.microforum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.songheqing.microforum.interceptor.TokenInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload-dir}")
    private String uploadDir;

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
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 静态资源映射：/uploads/** 映射到本地文件夹
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir + "/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册自定义拦截器对象
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns(
                        "/user/login", // 排除登录请求
                        "/user/register", // 排除注册请求
                        "/verifyRegisterCode", // 排除校验注册验证码请求
                        "/swagger-ui/**", // 排除Swagger请求
                        "/v3/api-docs/**", // 排除Swagger请求
                        // "/swagger-resources/**", // 排除Swagger请求
                        // "/webjars/**", // 排除Swagger请求
                        // "/v3/api-docs-ext/**", // 排除Swagger请求
                        // "/configuration/ui", // 排除Swagger请求
                        // "/configuration/security", // 排除Swagger请求
                        // "/swagger-ui.html", // 排除Swagger请求
                        "/favicon.ico", // <--- 新增，放行网站图标
                        "/uploads/**" // <--- 这里加上，放行图片静态资源
                // "/articles" // 排除文章请求
                );
    }
}