package com.demo.librarypoc.exception.handler;

import com.demo.librarypoc.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@ControllerAdvice
public class LibraryControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ErrorResponse.builder().message(ex.getMessage())
                .code("50001")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(MethodArgumentNotValidException ex,
                                                                       WebRequest request) {
        String message = ex.getBindingResult().getAllErrors().stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(ErrorResponse.builder().message(message)
                .code("50002")
                .status(HttpStatus.BAD_REQUEST)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(BookNotFoundException ex,
                                                                 WebRequest request) {
        return new ResponseEntity<>(ErrorResponse.builder().message(ex.getErrorMessage())
                .code(ex.getErrorCode())
                .status(ex.getHttpStatus())
                .build(), HttpStatus.NOT_FOUND);
    }

}
