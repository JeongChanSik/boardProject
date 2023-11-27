package com.semiproject.controllers;

import com.semiproject.commons.exceptions.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 예외 처리를 담당하는 공통 컨트롤러 어드바이스 클래스입니다.
 */
@Slf4j
@ControllerAdvice("com.semiproject.controllers")
public class CommonController {

    /**
     * 모든 예외에 대한 처리를 담당하는 메서드입니다.
     *
     * @param e        발생한 예외 객체
     * @param model    Model 객체
     * @param request  HttpServletRequest 객체
     * @param response HttpServletResponse 객체
     * @return 에러 페이지 뷰 경로
     */
    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e, Model model, HttpServletRequest request, HttpServletResponse response) {

        // 예외의 HTTP 상태 코드 설정
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
        }

        response.setStatus(status.value());

        // 에러 정보를 모델에 추가
        Map<String, String> attrs = new HashMap<>();
        attrs.put("status", String.valueOf(status.value()));
        attrs.put("path", request.getRequestURI());
        attrs.put("method", request.getMethod());
        attrs.put("message", e.getMessage());
        attrs.put("timestamp", LocalDateTime.now().toString());
        model.addAllAttributes(attrs);

        // 예외 스택 트레이스를 문자열로 변환하여 로깅
        Writer writer = new StringWriter();
        PrintWriter pr = new PrintWriter(writer);
        e.printStackTrace(pr);
        String errorMessage = ((StringWriter) writer).toString();
        log.error(errorMessage);

        // 에러 페이지 뷰 경로 반환
        return "error/common";
    }
}
