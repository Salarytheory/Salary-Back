package com.salary.common.util.util.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "페이지네이션 DTO")
@Getter
@AllArgsConstructor
public class PaginationDto<T> {
    @Schema(description = "결과데이터")
    private T result;

    @Schema(description = "총 개수")
    private long totalCount;
}
