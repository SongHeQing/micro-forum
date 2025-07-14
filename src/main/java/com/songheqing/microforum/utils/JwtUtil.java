package com.songheqing.microforum.utils;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.songheqing.microforum.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

/**
 * JWT 工具类
 * 
 * <p>
 * 提供 JWT 令牌的生成和解析功能
 * </p>
 * 
 * @author 宋和清
 * @version 1.0
 * @since 2024-07-14
 */
@Component
public class JwtUtil {

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 生成 JWT 令牌
     * 
     * @param claims 载荷数据
     * @return JWT 令牌字符串
     */
    public String generateToken(Map<String, Object> claims) {
        // 从配置中获取密钥
        SecretKey key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());

        // 从配置中获取过期时间
        long expirationTime = System.currentTimeMillis() + jwtConfig.getExpiration();

        return Jwts.builder()
                .signWith(key)
                .claims(claims)
                .expiration(new Date(expirationTime))
                .compact();
    }

    /**
     * 解析 JWT 令牌
     * 
     * @param token JWT 令牌字符串
     * @return 载荷数据
     */
    public Claims parseToken(String token) {
        // 从配置中获取密钥
        SecretKey key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 验证 JWT 令牌是否有效
     * 
     * @param token JWT 令牌字符串
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}