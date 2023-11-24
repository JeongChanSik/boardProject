package com.semiproject.commons;

import com.semiproject.commons.exceptions.AlertBackException;
import com.semiproject.commons.exceptions.AlertException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 예외를 자바스크립트 처리하는 공통 인터페이스
 */
public interface ScriptExceptionProcess {

    /**
     * AlertException을 처리하고 자바스크립트로 메시지를 출력하는 메서드
     *
     * @param e       처리할 예외 객체
     * @param model   Model 객체
     * @param response HttpServletResponse 객체
     * @return 스크립트를 실행하는 뷰 이름
     */
    @ExceptionHandler(AlertException.class)
    default String scriptHandler(AlertException e, Model model, HttpServletResponse response) {
        // 응답의 HTTP 상태 코드를 설정
        response.setStatus(e.getStatus().value());

        // 스크립트를 생성
        String script = String.format("alert('%s');", e.getMessage());

        // AlertBackException인 경우 뒤로 가기 스크립트를 추가
        if (e instanceof AlertBackException) {
            script += "history.back();";
        }

        // 모델에 스크립트를 추가
        model.addAttribute("script", script);

        // 실행할 스크립트를 반환
        return "common/_execute_script";
    }
}
