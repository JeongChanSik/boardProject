package com.semiproject.commons.exceptions;

import com.semiproject.commons.Utils;

/**
 * 잘못된 요청에 대한 예외 클래스로, 사용자에게 알림을 표시하고 이전 페이지로 이동시킵니다.
 */
public class BadRequestException extends AlertBackException {

    /**
     * 주어진 메시지를 사용하여 BadRequestException을 생성
     */
    public BadRequestException(String message) {
        super(message);
    }

    /**
     * 기본적인 BadRequestException을 생성하며, 기본 에러 메시지를 사용
     */
    public BadRequestException() {
        super(Utils.getMessage("BadRequest", "error"));
    }
}
