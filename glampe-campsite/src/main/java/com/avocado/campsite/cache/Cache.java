package com.avocado.campsite.cache;

public interface Cache<T> {
    T get(String key);
    void put(String key, T value);
    void remove(String key);
    boolean hasKey(String key);
}
