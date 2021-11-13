package com.demo.librarypoc.exception.handler;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String message;

    private String code;

    private HttpStatus status;
}
