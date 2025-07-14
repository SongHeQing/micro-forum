package com.songheqing.microforum.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置类
 * 
 * <p>
 * 从 application.yml 中读取 JWT 相关配置
 * </p>
 * 
 * @author 宋和清
 * @version 1.0
 * @since 2024-07-14
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    /**
     * JWT 密钥
     */
    private String secret;

    /**
     * JWT 过期时间（毫秒）
     */
    private Long expiration;

    // Getter 和 Setter 方法
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }
}