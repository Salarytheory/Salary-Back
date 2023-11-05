package com.salary;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "hello salary ver_0.1";
    }

    @GetMapping("health")
    public String checkHealth(){
        return "헬스체크용 api_ver_0.1";
    }
}
