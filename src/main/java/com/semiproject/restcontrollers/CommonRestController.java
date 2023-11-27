package com.semiproject.restcontrollers;

import com.semiproject.commons.exceptions.CommonException;
import com.semiproject.commons.rest.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.semiproject.restcontrollers")
public class CommonRestController {

    /**
     * 예외 처리 핸들러
     *
     * @param e 예외 객체
     * @return ResponseEntity<JSONData<Object>>
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONData<Object>> errorHandler(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        // CommonException 타입의 예외인 경우 상태 코드를 설정
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
        }

        // 예외 정보를 JSON 형태로 반환하는 데이터 객체 생성
        JSONData<Object> data = new JSONData<>();
        data.setSuccess(false);
        data.setStatus(status);
        data.setMessage(e.getMessage());

        e.printStackTrace(); // 예외 정보 출력

        return ResponseEntity.status(status).body(data); // ResponseEntity로 응답
    }
}
