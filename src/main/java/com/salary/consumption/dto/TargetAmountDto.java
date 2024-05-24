package com.salary.consumption.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "목표금액설정 dto")
public record TargetAmountDto(
        @Schema(description = "목표시작월", pattern = "yyyy-mm")
        String startTargetMonth,
        @Schema(description = "목표금액")
        long targetAmount
) {
}
