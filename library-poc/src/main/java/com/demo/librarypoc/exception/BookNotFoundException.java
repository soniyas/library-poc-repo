package com.demo.librarypoc.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BookNotFoundException extends Exception {
    private String errorMessage;
    private String errorCode;
    private HttpStatus httpStatus;

    public BookNotFoundException(String errorMessage, String errorCode, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

}
