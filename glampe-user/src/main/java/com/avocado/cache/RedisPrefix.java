package com.avocado.cache;

public enum RedisPrefix {
    USER,
    FCM_TOKEN,
    POINT_TRANSACTION;

    public String getPrefix() {
        return name() + "::";
    }
}
