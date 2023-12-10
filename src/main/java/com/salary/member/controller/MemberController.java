package com.salary.member.controller;

import com.salary.jwt.JwtToken;
import com.salary.jwt.JwtTokenProvider;
import com.salary.jwt.RefreshTokenRepository;
import com.salary.member.dto.SocialAuthInfoDto;
import com.salary.member.entity.Member;
import com.salary.member.service.MemberService;
import com.salary.member.service.StateManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 API", description = "로그인, 회원가입 등 유저와 관련된 API")
@RestController
@RequestMapping("/api/v1/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final StateManager stateManager;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/login-state")
    @Operation(summary = "소셜로그인 시도 시, 상태값 전달",
            description = "secretKey를 해싱한 상태값을 받아 저장한다. 이후 소셜로그인 시 함께오는 state와 일치여부를 검증한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "state값 저장 성공"),
            @ApiResponse(responseCode = "400", description = "state값 검증 실패"),
    })
    public ResponseEntity<Void> saveLoginState(@RequestHeader String state, @RequestHeader String nonce){
        try {
            stateManager.saveState(state, nonce);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/social-login")
    @Operation(summary = "소셜로그인", description = "사용자정보를 받아서 로그인 혹은 회원가입처리 후 jwt토큰을 헤더에 담아 반환한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공, 헤더에 access-token 담아서 반환"),
            @ApiResponse(responseCode = "400", description = "로그인 실패, state 혹은 sub 확인"),
            @ApiResponse(responseCode = "500", description = "서버에러")
    })
    public ResponseEntity<Void> socialLogin(@Parameter(description = "랜덤한 UUID 값")
                                                @RequestHeader("state") String state, @RequestBody SocialAuthInfoDto socialAuthInfo){
        try {
            Member member = memberService.socialLogin(state, socialAuthInfo);
            JwtToken jwtToken = jwtTokenProvider.createToken(member);
            refreshTokenRepository.save(jwtToken.getRefreshToken(), member.getSub());

            HttpHeaders headers = new HttpHeaders();
            headers.add("access-token", jwtToken.getAccessToken());
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            log.error("system error : ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping
    @Operation(summary = "초기화날짜 수정", description = "계획 및 목표금액이 초기화되는 날짜를 설정한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공, 헤더에 access-token 담아서 반환"),
            @ApiResponse(responseCode = "400", description = "날짜 범위초과 (1~31)")
    })
    public void setResetDay(@AuthenticationPrincipal Member member,
                            @RequestParam("reset-day") @Min(1) @Max(31) int resetDay){
        memberService.setResetDay(member, resetDay);
    }
}
