package com.salary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "HOME API", description = "서버체크용 API")
@RestController
@RequestMapping("/home")
public class HomeController {
    @Operation(summary = "서버체크", description = "호출하여 서버상태를 점검할때 사용한다")
    @ApiResponse(responseCode = "200", description = "api버전을 반환",
            content = @Content(schema = @Schema(implementation = String.class)))
    @GetMapping("health")
    public String checkHealth(){
        return "api_ver_0.0.5";
    }

    @GetMapping("auth-check")
    public String checkAuth(){
        return "auth-is-ok";
    }
}
