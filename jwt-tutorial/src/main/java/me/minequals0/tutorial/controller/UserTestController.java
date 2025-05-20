package me.minequals0.tutorial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// JWT 토큰을 로그인 응답에서 추출해 Postman의 Scripts에 저장
// 다른 보호된 API 요청에 자동으로 붙여서 인증 테스트하기
@RestController
@RequestMapping("/api/user")
public class UserTestController {

    @GetMapping("/hello")
    public ResponseEntity<String> helloUser() {
        return ResponseEntity.ok("Hello, authenticated user!");
    }
}