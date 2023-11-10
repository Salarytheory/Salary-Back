package com.salary.member.entity;

import com.salary.global.EnumModel;

public enum Provider implements EnumModel {
    GOOGLE("google"), APPLE("apple"), KAKAO("kakao");

    private final String value;

    Provider(String value) {
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
