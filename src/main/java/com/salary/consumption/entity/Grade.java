package com.salary.consumption.entity;

import com.salary.global.EnumModel;

public enum Grade implements EnumModel {
    STUPID("STUPID"), GREAT("GREAT");

    private final String value;

    Grade(String value) {
        this.value = value;
    }


    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
