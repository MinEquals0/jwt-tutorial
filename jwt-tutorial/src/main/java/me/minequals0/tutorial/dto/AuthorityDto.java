package me.minequals0.tutorial.dto;

import lombok.*;

// Response 시 DTO를 통해서만 응답하도록 사용
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityDto {
    private String authorityName;
}
