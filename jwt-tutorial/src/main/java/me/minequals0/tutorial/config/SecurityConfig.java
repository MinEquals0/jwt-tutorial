package me.minequals0.tutorial.config;


import me.minequals0.tutorial.jwt.*;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// HTTP 요청에 대한 보안 설정을 정의
// 인증과 권한 부여, 예외 처리, 세션 관리 등을 처리하는 HTTP 요청을 구성하는 역할
@Configuration  // 설정 클래스, 스프링이 빈으로 등록
@EnableWebSecurity // Spring Security 설정을 활성화함
@EnableMethodSecurity // 메서드 수준에서 권한 체크를 가능하게 해줌
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // 생성자, DI
    public SecurityConfig(TokenProvider tokenProvider,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    // 비밀번호 암호화를 위한 빈 등록 (BCrypt 알고리즘 사용)
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 토큰을 사용하니까 csrf는 disable
                .csrf(AbstractHttpConfigurer::disable)

                // 인증, 인가 예외 발생 시 처리할 핸들러 등록 - 추가, 이후 수정
//                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class) // filter 교체
                .exceptionHandling(exceptionHandling -> exceptionHandling
                    .accessDeniedHandler(jwtAccessDeniedHandler)  // 권한 없는 경우 (403)
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 인증 안 된 경우 (401)
                        )

                // 특정 API 경로는 인증 없이 접근 허용
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/api/hello",
                                "/h2-console/**",   // H2 DB 웹 콘솔 접근 허용
                                "/api/authenticate", // 로그인 API
                                "/api/signup").permitAll() // 회원가입 API
                        // 위의 API들은 토큰이 없는 상태에서 요청이 들어오기 때문에 모두 permitAll 설정
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .anyRequest().authenticated() // 그 외 요청은 인증 필요
                )

                // 세션을 사용하지 않기 때문에 STATELESS로 설정 - 추가
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // H2 콘솔을 위한 설정 (iframe 허용)
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )

                // 커스텀 JWT 보안 설정 적용 (JwtFilter 등록됨)
                .with(new JwtSecurityConfig(tokenProvider), customizer -> {});
        return http.build();
    }
}
