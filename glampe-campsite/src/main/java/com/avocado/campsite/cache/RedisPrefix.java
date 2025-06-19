package com.avocado.campsite.cache;

public enum RedisPrefix {
    CAMPSITE;

    public String getPrefix() {
        return name() + "::";
    }
}
