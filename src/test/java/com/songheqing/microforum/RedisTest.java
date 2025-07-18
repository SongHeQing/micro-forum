package com.songheqing.microforum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testSetGetDelete() {
        String key = "test:key";
        String value = "hello redis";

        // set
        redisTemplate.opsForValue().set(key, value);

        // get
        String result = redisTemplate.opsForValue().get(key);
        Assertions.assertEquals(value, result);

        // delete
        redisTemplate.delete(key);
        String deleted = redisTemplate.opsForValue().get(key);
        Assertions.assertNull(deleted);
    }
}
