package com.utopian.weather.configuration;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration extends CachingConfigurerSupport {

// ToDo: include spring-data-redis
/*
    @Bean
    public CacheManager cacheManager(JedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofHours(2)))
                .build();
    }
*/
}
