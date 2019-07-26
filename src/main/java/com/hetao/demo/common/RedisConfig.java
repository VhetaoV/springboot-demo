package com.hetao.demo.common;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import java.time.Duration;

public class RedisConfig extends CachingConfigurerSupport {


    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(){

        FastJson2JsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJson2JsonRedisSerializer<>(Object.class);
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(SerializationPair.fromSerializer(fastJsonRedisSerializer)).entryTtl(Duration.ofDays(1));
        return configuration;
    }

}
