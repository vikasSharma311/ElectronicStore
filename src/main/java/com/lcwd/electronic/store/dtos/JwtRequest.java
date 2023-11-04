package com.lcwd.electronic.store.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class JwtRequest {
    private String email;
    private String password;
}
