package com.salary.consumption.controller;

import com.salary.consumption.dto.ConsumptionRecordDto;
import com.salary.consumption.dto.ConsumptionSummaryDto;
import com.salary.consumption.dto.StupidConsumptionCurrentSituationDto;
import com.salary.consumption.dto.TargetAmountDto;
import com.salary.consumption.service.ConsumptionService;
import com.salary.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "지출 API", description = "지출 관련 API")
@RestController
@RequestMapping("/api/v1/consumption")
@Slf4j
@RequiredArgsConstructor
public class ConsumptionController {
    private final ConsumptionService consumptionService;

    @GetMapping("/summary")
    @Operation(summary = "지출현황요약", description = "계획한금액, 소비금액, 남은금액을 반환한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회성공")
    })
    public ResponseEntity<ConsumptionSummaryDto> getSummary(@AuthenticationPrincipal Member member,
                                                            @Parameter(description = "기준연월 (yyyy-MM)") @PathVariable("base-date") String baseDate){
        ConsumptionSummaryDto consumptionSummaryDto = consumptionService.getSummary(member, baseDate);
        return new ResponseEntity<>(consumptionSummaryDto, HttpStatus.OK);
    }

    @GetMapping("/stupid-situation")
    @Operation(summary = "스튜핏 소비 현황 조회", description = "스튜핏, 그레잇 소비금액 및 비율을 조회한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회성공")
    })
    public ResponseEntity<StupidConsumptionCurrentSituationDto> getStupidConsumptionCurrentSituation(@AuthenticationPrincipal Member member){
        StupidConsumptionCurrentSituationDto result = consumptionService.getStupidConsumptionCurrentSituation(member);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "지출내역기록", description = "지출내역을 저장한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저장성공"),
            @ApiResponse(responseCode = "400", description = "디폴트 카테고리 이외의 카테고리 선택 (추후 변경)")
    })
    public ResponseEntity<Void> record(@AuthenticationPrincipal Member member, @RequestBody ConsumptionRecordDto consumptionRecordDto){
        try {
            consumptionService.record(member, consumptionRecordDto);
        }catch (RuntimeException e){
            log.info("business-logic-error : ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{base-date}")
    @Operation(summary = "지출내역조회(미완성)", description = "월별 지출 내역을 조회한다")
    public void getConsumptionHistory(@AuthenticationPrincipal Member member,
            @Parameter(description = "조회하려는 연월 (ex : 2023-12)") @PathVariable("base-date") String baseDate){
        //TODO
    }
}
