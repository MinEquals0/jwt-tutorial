package me.minequals0.tutorial.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import me.minequals0.tutorial.dto.UserDto;
import me.minequals0.tutorial.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // API 테스트용 메서드
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    // 클라이언트를 /api/user로 리다이렉트, 302 Found 응답 보냄
    @PostMapping("/test-redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/user");
    }

    // 회원 가입
    // 요청 본문으로 받은 사용자 정보를 검증(@Valid)한 뒤,
    // 서비스에서 회원 가입 처리를 수행하고 결과를 반환
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    // 현재 로그인된 사용자의 정보를 가져옴
    // 현재 인증된 사용자의 정보를 UserDto 형태로 반환
    @GetMapping("/user")
    // 이 API는 USER, ADMIN 권한을 가진 사용자만 접근할 수 있음
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserDto> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    // 특정 유저의 정보를 조회
    // 경로에서 전달받은 username에 해당하는 사용자의 정보를 반환
    @GetMapping("/user/{username}")
    // ADMIN 권한이 있어야 이 API를 호출 가능
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
    }


}
