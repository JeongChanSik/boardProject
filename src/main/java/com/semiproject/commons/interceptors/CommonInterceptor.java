package com.semiproject.commons.interceptors;

import com.semiproject.commons.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CommonInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        init(request);

        return true;
    }
    // 초기화 작업
    private void init(HttpServletRequest request) {
        HttpSession session = request.getSession();

        /* PC, Mobile 수동 변경 처리 Start */
        String device = request.getParameter("device");
        if (device != null && !device.isBlank()) {
            session.setAttribute("device", device.toLowerCase().equals("mobile")?"mobile":"pc");
        }
        /* PC, Mobile 수동 변경 처리 End */

        /*로그인 페이지가 아닐 경우 로그인 유효성 검사 (세션 삭제 처리) */
        String URI = request.getRequestURI(); // 주소에 멤버가 포함되어 있는지 확인 여부 체크를 위한 변수
        if(URI.indexOf("/member/login") == -1) { // 로그인 페이지가 아닐 경우 세션 삭제 쳐리
            Utils.loginInit(session);
        }
    }
}