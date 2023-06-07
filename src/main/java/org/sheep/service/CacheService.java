package org.sheep.service;

public interface CacheService<T> {
    T get(String key);

    void add(String key, T value);
}
