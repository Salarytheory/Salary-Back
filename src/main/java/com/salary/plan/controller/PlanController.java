package com.salary.plan.controller;

import com.salary.consumption.dto.MonthlyPlanSetDto;
import com.salary.consumption.dto.TargetAmountDto;
import com.salary.member.entity.Member;
import com.salary.plan.dto.MonthlyPlanDto;
import com.salary.plan.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "계획 API", description = "계획 관련 API")
@RestController
@RequestMapping("/api/v1/plan")
@Slf4j
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

    @PostMapping("/target-amount")
    @Operation(summary = "목표금액설정", description = "매월 설정한 계획금액을 전달하여 저장한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저장성공"),
            @ApiResponse(responseCode = "400", description = "중복목표설정")
    })
    public ResponseEntity<Void> saveTargetAmount(@AuthenticationPrincipal Member member,
                                                 @RequestBody TargetAmountDto targetAmountDto){
        try {
            planService.setTargetAmount(member, targetAmountDto);
        }catch (DataIntegrityViolationException e){
            log.info("business-logic-error", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "월별계획현황조회", description = "유저의 초기화날짜에 따라서 앱에서 기준월을 계산하여 전달해줘야함." +
            "ex) 현재날짜가 2023/11/26, 초기화날짜가 25일인 경우 기준년월은 2023-11이 아닌 2023-12")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회성공")
    })
    public ResponseEntity<List<MonthlyPlanDto>> getMonthlyPlan(@AuthenticationPrincipal Member member,
                                                               @RequestParam("base-date") String baseDate){
        List<MonthlyPlanDto> result = planService.getMonthlyPlan(member, baseDate);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "월별, 카테고리별 목표소비금액 설정", description = "월별과 카테고리별로 목표소비금액을 설정한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회성공"),
            @ApiResponse(responseCode = "400", description = "카테고리 정보 없음")
    })
    public ResponseEntity<Void> setTargetConsumptionPlan(@AuthenticationPrincipal Member member,
                                         @RequestBody List<MonthlyPlanSetDto> monthlyPlanSetDtoList){
        try {
            planService.setTargetConsumptionPlan(member, monthlyPlanSetDtoList);
        }catch (RuntimeException e){
            log.info("business-logic-error", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
