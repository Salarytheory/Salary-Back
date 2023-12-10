package com.salary.plan.controller;

import com.salary.consumption.dto.TargetAmountDto;
import com.salary.member.entity.Member;
import com.salary.plan.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "계획 API", description = "계획 관련 API")
@RestController
@RequestMapping("/api/v1/plan")
@Slf4j
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

    @PostMapping("/target-amount")
    @Operation(summary = "월별 목표금액설정", description = "매월 설정한 계획금액을 전달하여 저장한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저장성공")
    })
    public ResponseEntity<Void> saveTargetAmount(@AuthenticationPrincipal Member member,
                                                 @RequestBody TargetAmountDto targetAmountDto){
        planService.setTargetAmount(member, targetAmountDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
