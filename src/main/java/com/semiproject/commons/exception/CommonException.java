package com.semiproject.commons.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class CommonException extends RuntimeException{

    private HttpStatus status;

    public CommonException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }
    
    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    // 공통 예외고 상태 관리를 위해서 추가함
    private HttpStatus getStatus() {
        return status;

    }
}
