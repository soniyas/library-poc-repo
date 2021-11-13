package com.demo.librarypoc.exception.handler;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    public String message;

    public String code;

    public HttpStatus status;
}
