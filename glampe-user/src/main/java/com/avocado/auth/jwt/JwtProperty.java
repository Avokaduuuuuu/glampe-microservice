package com.avocado.auth.jwt;

public enum JwtProperty {
    TOKEN_EXPIRATION(1000 * 60 * 60 * 48L);

    private final Long value;

    JwtProperty(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
