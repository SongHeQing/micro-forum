package com.songheqing.microforum.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {
    private String endpoint;
    private String publicUrl;
    private String accessKey;
    private String secretKey;
    private Map<String, String> buckets = new HashMap<>();

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    /**
     * 获取公共存储桶名称
     * 
     * @return 公共存储桶名称
     */
    public String getPublicBucket() {
        return buckets.get("public");
    }

    /**
     * 获取私有存储桶名称
     * 
     * @return 私有存储桶名称
     */
    public String getPrivateBucket() {
        return buckets.get("private");
    }
}