package com.salary.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유저정보 dto")
public record MemberInfoDto(
        @Schema(description = "닉네임")
        String name,
        @Schema(description = "연동된 소셜이름")
        String provider,
        @Schema(description = "초기화날짜")
        int resetDay,
        @Schema(description = "통화단위")
        String currencyUnit
) {
}
