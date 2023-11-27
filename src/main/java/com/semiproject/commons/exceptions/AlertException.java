package com.semiproject.commons.exceptions;

import org.springframework.http.HttpStatus;

/**
 * 알림 관련 예외 클래스로, 사용자에게 특정 메시지를 전달하는 데 사용
 */

public class AlertException extends CommonException {
    public AlertException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
