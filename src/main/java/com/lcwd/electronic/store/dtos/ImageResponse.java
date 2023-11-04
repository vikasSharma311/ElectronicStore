package com.lcwd.electronic.store.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class ImageResponse {
    private String imageName;
    private String message;
    private boolean success;
    private HttpStatus status;
}
