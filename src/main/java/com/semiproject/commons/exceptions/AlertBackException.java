package com.semiproject.commons.exceptions;

public class AlertBackException extends AlertException {

    /**
     * 사용자에게 알림을 표시하고 이전 페이지로 이동시키는 예외 클래스
     */

    public AlertBackException(String message) {
        super(message);
    }
}
