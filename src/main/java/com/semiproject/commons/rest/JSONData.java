package com.semiproject.commons.rest;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class JSONData<T> {
    private boolean success = true;
    private HttpStatus status = HttpStatus.OK;
    private T data; // 성공 시 전달할 데이터
    private String message; // success가 false일 때 뜨는 메세지

}
