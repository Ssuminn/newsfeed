package com.pokemon.newsfeed.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


public class GlobalExceptionHandler {

    // 예외 처리 메서드 예시
    // 여기서는 모든 Exception 타입의 예외를 처리
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<String> handleAllExceptions(Exception ex, WebRequest request) {
        // 예외에 대한 간단한 메시지와 함께 INTERNAL_SERVER_ERROR (500) 상태 코드를 반환
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 특정 예외 타입을 처리하는 메서드 예시
    // CustomException이라는 사용자 정의 예외를 처리
    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<String> handleUserNotFoundException(CustomException ex, WebRequest request) {
        // CustomException의 경우, NOT_FOUND (404) 상태 코드를 반환
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
