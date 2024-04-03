package com.salary.consumption.controller;

import com.salary.consumption.dto.*;
import com.salary.consumption.service.ConsumptionService;
import com.salary.member.entity.Member;
import com.salary.common.util.util.dto.PaginationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "지출 API", description = "지출 관련 API")
@RestController
@RequestMapping("/api/v1/consumption")
@Slf4j
@RequiredArgsConstructor
public class ConsumptionController {
    private final ConsumptionService consumptionService;

    @GetMapping("/summary/{base-date}")
    @Operation(summary = "지출현황요약", description = "계획한금액, 소비금액, 남은금액을 반환한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회성공")
    })
    public ResponseEntity<ConsumptionSummaryDto> getSummary(@AuthenticationPrincipal Member member,
                                                            @Parameter(description = "기준연월 (yyyy-MM)") @PathVariable("base-date") String baseDate){
        ConsumptionSummaryDto consumptionSummaryDto = consumptionService.getSummary(member, baseDate);
        return new ResponseEntity<>(consumptionSummaryDto, HttpStatus.OK);
    }

    @GetMapping("/stupid-situation/{base-date}/{is-widget}")
    @Operation(summary = "스튜핏 소비 현황 조회", description = "스튜핏, 그레잇 소비금액 및 비율을 조회한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회성공")
    })
    public ResponseEntity<StupidConsumptionCurrentSituationDto> getStupidConsumptionCurrentSituation(
            @AuthenticationPrincipal Member member,
            @Parameter(description = "기준연월 (yyyy-MM)") @PathVariable("base-date") String baseDate,
            @Parameter(description = "위젯o : Y, 위젯x : N") @PathVariable("is-widget") String isWidget){
        StupidConsumptionCurrentSituationDto result = consumptionService.getStupidConsumptionCurrentSituation(member, baseDate, isWidget);
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

    @GetMapping("/monthly/{base-date}")
    @Operation(summary = "지출내역조회", description = "월별 지출 내역을 조회한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회성공")
    })
    public ResponseEntity<PaginationDto<List<ConsumptionHistoryDto>>> getConsumptionHistory(
            @AuthenticationPrincipal Member member,
            @Parameter(description = "조회하려는 연월 (ex : 2023-12)") @PathVariable("base-date") String baseDate,
            @Parameter(description = "페이지번호") @RequestParam int page,
            @Parameter(description = "페이지 당 개수") @RequestParam int size){
        PaginationDto<List<ConsumptionHistoryDto>> result = consumptionService.getMonthlyConsumptionHistory(member, baseDate, PageRequest.of(page, size));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "지출내역상세조회", description = "지출 상세 내역을 조회한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회성공")
    })
    public ResponseEntity<ConsumptionHistoryDto> getConsumptionHistory(@Parameter(description = "지출내역 시퀀스") @PathVariable Long id){
        ConsumptionHistoryDto result = consumptionService.getConsumptionHistory(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "지출상세내역 수정", description = "지출 상세 내역을 수정한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정성공"),
            @ApiResponse(responseCode = "400", description = "카테고리 없음 or id값 존재하지않음")
    })
    public ResponseEntity<Void> modify(@Parameter(description = "지출내역 시퀀스") @PathVariable Long id,
                       @RequestBody ConsumptionRecordDto consumptionRecordDto){
        try {
            consumptionService.modify(id, consumptionRecordDto);
        } catch (RuntimeException e){
            log.info("business-logic-error : ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "지출상세내역 삭제", description = "지출 상세 내역을 삭제한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제성공"),
            @ApiResponse(responseCode = "400", description = "id값 존재하지않음")
    })
    public ResponseEntity<Void> remove(@Parameter(description = "지출내역 시퀀스") @PathVariable Long id){
        try {
            consumptionService.remove(id);
        }catch (Exception e){
            log.error("system-error : ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
