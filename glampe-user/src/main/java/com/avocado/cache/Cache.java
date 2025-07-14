package com.avocado.cache;

public interface Cache<T>{
    T get(String key);
    void put(String key, T value);
    void remove(String key);
    boolean hasKey(String key);
}
