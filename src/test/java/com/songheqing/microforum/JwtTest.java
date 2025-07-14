package com.songheqing.microforum;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.songheqing.microforum.utils.JwtUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * JWT 令牌生成测试类
 * 
 * 问题解决总结：
 * 1. 依赖问题：添加了缺失的 JJWT 依赖
 * - jjwt-impl：运行时实现
 * - jjwt-jackson：JSON 序列化支持
 * 
 * 2. API 弃用问题：更新了已弃用的方法
 * - addClaims() → claims()
 * - setExpiration() → expiration()
 * - signWith(SignatureAlgorithm, String) → signWith(Key)
 * 
 * 密钥管理说明：
 * - 自动生成密钥：每次运行生成新密钥，仅适用于测试
 * - 手动指定密钥：使用固定密钥，适用于生产环境
 * * 密钥长度必须 >= 256位（32字节）
 * * 建议使用配置文件或环境变量存储
 * * 确保密钥在所有服务实例间保持一致
 */
@SpringBootTest
public class JwtTest {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 测试生成 JWT 令牌
     */
    @Test
    public void testGenJwt() {
        // 创建 JWT 载荷数据
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 10); // 用户ID
        claims.put("username", "itheima"); // 用户名

        // 方式1：手动指定密钥（推荐）
        String secretString = "your-256-bit-secret-key-here-make-it-long-enough-for-security";

        // 验证密钥长度
        System.out.println("平台默认编码: " + System.getProperty("file.encoding"));
        System.out.println("默认字符集: " + java.nio.charset.Charset.defaultCharset());
        System.out.println("字符串长度: " + secretString.length() + " 字符");
        System.out.println("平台编码字节长度: " + secretString.getBytes().length + " 字节");
        System.out.println(
                "UTF-8编码字节长度: " + secretString.getBytes(java.nio.charset.StandardCharsets.UTF_8).length + " 字节");
        System.out.println("位长度: " + (secretString.getBytes().length * 8) + " 位");

        // 算法推断规则：
        // 位长度 >= 256位 → HS256
        // 位长度 >= 384位 → HS384
        // 位长度 >= 512位 → HS512
        System.out.println("推断算法: " + (secretString.getBytes().length * 8 >= 512 ? "HS512"
                : secretString.getBytes().length * 8 >= 384 ? "HS384" : "HS256"));

        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes()); // 自动推断算法

        // 方式2：自动生成密钥（仅用于测试）
        // SecretKey key = Jwts.SIG.HS256.key().build();

        // 方式3：如果您想要明确指定算法，可以这样写：
        // SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes()); // 密钥长度决定算法
        // 密钥长度 >= 256位 → HS256
        // 密钥长度 >= 384位 → HS384
        // 密钥长度 >= 512位 → HS512

        // 构建 JWT 令牌
        // 设置签名算法、载荷数据、过期时间（12小时后）

        /**
         * 旧版本写法（已弃用）：
         * String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, "aXRjYXN0")
         * .addClaims(claims)
         * .setExpiration(new Date(System.currentTimeMillis() + 12 * 3600 * 1000))
         * .compact();
         * 
         * 新版本写法（手动指定密钥）：
         * String secretString =
         * "your-256-bit-secret-key-here-make-it-long-enough-for-security";
         * SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes()); // 自动推断为 HS256
         * String jwt = Jwts.builder().signWith(key).claims(claims)
         * .expiration(new Date(System.currentTimeMillis() + 12 * 3600 * 1000))
         * .compact();
         */
        String jwt = Jwts.builder()
                .signWith(key) // 使用密钥签名
                .claims(claims) // 设置载荷数据
                .expiration(new Date(System.currentTimeMillis() + 12 * 3600 * 1000)) // 设置过期时间 12小时
                .compact(); // 生成最终的 JWT 字符串

        System.out.println(jwt);
    }

    @Test
    public void testParseJwt() {

        /**
         * Claims claims = Jwts.parser().setSigningKey("aXRjYXN0")
         * .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTAsInVzZXJuYW1lIjoiaXRoZWltYSIsImV4cCI6MTcwMTkwOTAxNX0.N-MD6DmoeIIY5lB5z73UFLN9u7veppx1K5_N_jS9Yko")
         * .getBody();
         * System.out.println(claims);
         */
        // 创建密钥（与生成时使用相同的密钥）
        SecretKey key = Keys.hmacShaKeyFor("your-256-bit-secret-key-here-make-it-long-enough-for-security".getBytes());

        // 解析 JWT 令牌
        Claims claims = Jwts.parser()
                .verifyWith(key) // 使用 verifyWith 替代 setSigningKey
                .build()
                .parseSignedClaims(
                        "eyJhbGciOiJIUzM4NCJ9.eyJpZCI6MTAsInVzZXJuYW1lIjoiaXRoZWltYSIsImV4cCI6MTc1MjUwMzQyOH0.OVLBP7cEaCAFewYOq_6tIo6T7SNV08Np2HgISOTwufIl654eeSVJKvOxE6SaoKmj")
                .getPayload();
        System.out.println(claims);

        /**
         * 使用 verifyWith(key) 替代 setSigningKey(String)
         * 添加了 .build() 方法调用
         * 使用 parseSignedClaims 替代 parseClaimsJws
         * 使用 getPayload() 替代 getBody()
         */
    }

    /**
     * 测试使用 YAML 配置的 JWT 工具类
     */
    @Test
    public void testJwtUtilWithYamlConfig() {
        // 创建测试载荷数据
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 100);
        claims.put("username", "testuser");
        claims.put("role", "admin");

        // 使用工具类生成 JWT 令牌
        String token = jwtUtil.generateToken(claims);
        System.out.println("=== YAML配置JWT测试 ===");
        System.out.println("生成的JWT: " + token);

        // 使用工具类解析 JWT 令牌
        Claims parsedClaims = jwtUtil.parseToken(token);
        System.out.println("解析的载荷: " + parsedClaims);
        System.out.println("用户ID: " + parsedClaims.get("id"));
        System.out.println("用户名: " + parsedClaims.get("username"));
        System.out.println("角色: " + parsedClaims.get("role"));

        // 验证令牌是否有效
        boolean isValid = jwtUtil.validateToken(token);
        System.out.println("令牌是否有效: " + isValid);

        // 测试无效令牌
        boolean isInvalidTokenValid = jwtUtil.validateToken("invalid.token.here");
        System.out.println("无效令牌是否有效: " + isInvalidTokenValid);
    }
}