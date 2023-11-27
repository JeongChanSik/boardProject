package com.semiproject.commons.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * 일반적인 예외를 나타내는 기본 클래스로, HTTP 상태 코드와 함께 예외 메시지를 가지고 있다
 */
@Setter
@Getter
public class CommonException extends RuntimeException {

    /**
     * HTTP 상태 코드를 저장하는 변수
     */
    private HttpStatus status;

    /**
     * 주어진 메시지와 기본 HTTP 상태 코드(500 - Internal Server Error)를 사용하여 CommonException을 생성
     *
     * @param message 예외 메시지
     */
    public CommonException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 주어진 메시지와 HTTP 상태 코드를 사용하여 CommonException을 생성
     *
     * @param message 예외 메시지
     * @param status  HTTP 상태 코드
     */
    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
