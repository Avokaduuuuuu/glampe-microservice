package com.avocado.config;

import lombok.Getter;

@Getter
public enum SystemProperties {
    SYSTEM_FEE(0.1);

    private final Double value;

    SystemProperties(Double value) {
        this.value = value;
    }

}
