package com.lcwd.electronic.store.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class JwtResponse {
    private String jwtToken;
    private UserDto user;
}
