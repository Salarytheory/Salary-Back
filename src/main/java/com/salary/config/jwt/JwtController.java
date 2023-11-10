package com.salary.config.jwt;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "jwt API", description = "jwt 토큰과 관련된 api")
@RestController
@RequestMapping("/api/v1/jwt")
@RequiredArgsConstructor
public class JwtController {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @GetMapping("/access-token")
    @Operation(summary = "accessToken 재발급 API",
            description = "액세스토큰을 유저키값(sub)을 사용하여 재발급받는다. 리프레시 토큰이 만료된 경우 재발급할수없다. 재로그인필요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "헤더에 access-token 담아서 반환"),
            @ApiResponse(responseCode = "400", description = "리프레시 토큰 만료. 재로그인 필요")
    })
    public ResponseEntity<Void> getAccessToken(@RequestParam String sub){
        if(refreshTokenRepository.validate(sub)){
            String accessToken = jwtTokenProvider.createAccessToken(sub);
            HttpHeaders headers = new HttpHeaders();
            headers.add("access-token", accessToken);
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
