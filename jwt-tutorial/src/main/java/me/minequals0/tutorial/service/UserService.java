package me.minequals0.tutorial.service;

import java.util.Collections;
import me.minequals0.tutorial.dto.UserDto;
import me.minequals0.tutorial.entity.Authority;
import me.minequals0.tutorial.entity.User;
import me.minequals0.tutorial.exception.DuplicateMemberException;
import me.minequals0.tutorial.exception.NotFoundMemberException;
import me.minequals0.tutorial.repository.UserRepository;
import me.minequals0.tutorial.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 회원가입, 유저 정보 조회
@Service
public class UserService {
    // UserRepo, PasswordEncoder DI
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 로직을 수행
    // 파라미터로 받은 username이 DB에 존재하지 않으면 Authority와 User 정보를 생성해서
    // UserRepository의 save 메소드를 통해 DB에 저장
    @Transactional
    public UserDto signup(UserDto userDto) {
        // DB에 유저가 가입돼 있는지 확인
        if(userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        // 없는 경우 권한 정보 만듦
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        // 유저 정보도 만듦
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user));
    }

    // 유저 권한 정보 가져오는 2개 메서드
    // 각각 허용 권한 다르게 해서 권한 검증 테스트에 사용

    // username 기준으로 정보 가져옴
    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }


    // 현재 Security Context에 저장된 username의 정보만 가져옴
    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }

}
