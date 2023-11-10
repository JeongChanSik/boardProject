package com.semiproject.commons.rest;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@RequiredArgsConstructor // 데이터를 추가하기 위해 필요
public class JSONData<T> {
    private boolean success = true;
    private HttpStatus status = HttpStatus.OK;

    @NonNull
    private T data; // 성공 시 전달할 데이터
    private String message; // success가 false일 때 뜨는 메세지

}
