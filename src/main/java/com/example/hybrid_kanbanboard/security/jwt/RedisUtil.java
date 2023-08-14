package com.example.hybrid_kanbanboard.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;

    public String getData(String key) { // key 를 통해 value(데이터)를 얻는다.
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        log.info("key : " + key);
        return valueOperations.get(key);
    }

    public boolean existData(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    // 이메일 인증 코드 전송
    public void setDataExpire(String key, String value, long duration) {
        //  duration 동안 (key, value)를 저장한다.
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);

        // 인증 코드 값을 value 에 저장
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteData(String key) {
        // 데이터 삭제
        redisTemplate.delete(key);
    }

    public void save(String key, String value, long duration) {
        //  duration 동안 (key, value)를 저장한다.
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);

        // 인증 코드 값을 value 에 저장
        valueOperations.set(key, value, expireDuration);
    }

    // redis 에서 getEmail 을 꺼내서 사용
    public String getEmail(String key) { // key 를 통해 value(데이터)를 얻는다.
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        log.info("key : " + key);
        return valueOperations.get(key);
    }
}
