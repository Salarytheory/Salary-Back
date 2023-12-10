package com.salary.consumption.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "목표금액설정 dto")
public record TargetAmountDto(
        @Schema(description = "목표연월", pattern = "yyyy-mm")
        String targetDate,
        @Schema(description = "목표금액")
        long targetAmount
) {
}
