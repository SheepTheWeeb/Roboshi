package org.sheep.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CacheServiceImpl<T> implements CacheService<T> {

    private final Cache<String, T> cache;

    public CacheServiceImpl(int expireTime, TimeUnit timeUnit) {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expireTime, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    @Override
    public T get(String key) {
        return cache.getIfPresent(key);
    }

    @Override
    public void add(String key, T value) {
        if (key != null && value != null) {
            cache.put(key, value);
            log.info("Stored '{}' in the cache", key);
        } else {
            log.warn("Could not store '{}' in the cache", key);
        }
    }
}
