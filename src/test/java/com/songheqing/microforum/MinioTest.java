package com.songheqing.microforum;

import com.songheqing.microforum.config.MinioConfig;
import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {MinioConfig.class})
public class MinioTest {

    @Autowired
    private MinioClient minioClient;

    @Test
    public void testMinioClient() {
        assertThat(minioClient).isNotNull();
        System.out.println("MinIO Client 创建成功");
    }
}