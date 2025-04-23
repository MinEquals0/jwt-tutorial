package me.minequals0.tutorial.dto;

import lombok.*;

// token 정보를 응답 시 사용
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private String token;

}
