package com.songheqing.microforum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**") // 匹配所有接口
        .allowedOriginPatterns("http://localhost:5174") // 明确允许的域名
        .allowedMethods("*") // 允许所有HTTP方法
        .allowedHeaders("Content-Type", "Authorization", "X-Requested-With") // 限制请求头
        .allowCredentials(true) // 允许凭证
        .maxAge(3600); // 预检请求缓存时间（秒），减少OPTIONS请求频率
  }
}