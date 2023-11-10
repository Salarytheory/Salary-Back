package com.salary.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "소셜로그인 시, 사용자 정보를 받는 dto")
public record SocialAuthInfoDto(
        @Schema(description = "sub(유저키값)")
        String sub,
        @Schema(description = "이메일")
        String email,
        @Schema(description = "OAuth 제공자(GOOGLE, KAKAO, APPLE)")
        String provider
) {
}
